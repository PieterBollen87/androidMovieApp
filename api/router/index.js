'use strict'

const router = require('express').Router();
const fs = require('fs');
const multer = require('multer')
const toId = require('toid');

const films = require('../data/films.json');
const userMovies = require('../data/usermovies.json');
const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, 'public/images/')
    },
    filename: function (req, file, cb) {
        cb(null, `${file.originalname}.jpg`)
    }
})
const upload = multer({ storage: storage })
const admin = require("firebase-admin");

// Firebase
const serviceAccount = require("../serviceAccountKey.json");
const firebaseTopic = "news";

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://main-c430c.firebaseio.com/"
});


router.get('/films', (req, res) => {
    res.json(films);
});

router.post('/addfilm', upload.single('poster'), (req, res) => {
    if (!req.body.title) return res.status(500).json({ error: 'No film title' });
    if (!req.body.director) return res.status(500).json({ error: 'No film director' });
    if (!req.body.year) return res.status(500).json({ error: 'No film year' });
    if (!req.body.genre) return res.status(500).json({ error: 'No film genre' });
    if (!req.body.trailer) return res.status(500).json({ error: 'No film trailer' });
    if (Object.keys(films).find(f => toId(films[f].title) === toId(req.body.title)))
        return res.status(500).json({ error: 'Film already exists' });

    const film = {
        id: films.length + 1,
        title: req.body.title,
        director: req.body.director,
        year: parseInt(Number(req.body.year)),
        genre: req.body.genre,
        trailer: req.body.trailer,
    };

    films.push(film);

    fs.writeFile('data/films.json', JSON.stringify(films), (err) => {
        if (err) {
            res.status(500).json({ success: false })
        } else {
            // firebase push notification
            const payload = {
                notification: {
                    title: `New movie!`,
                    body: `The new movie '${film.title}' has been added to our database!`
                }
            };

            // Send a message to devices subscribed to the provided topic.
            admin.messaging().sendToTopic(firebaseTopic, payload)
                .then(response => {
                    //console.log("Successfully sent message:", response);
                })
                .catch(error => {
                    console.log("Error sending message:", error);
                });
            res.status(200).json({ success: true });
        }
    });
});

router.delete('/deletefilm/:id', (req, res) => {
    const filmid = req.params.id;
    const film = Object.keys(films).find(f => Number(films[f].id) === Number(filmid));

    if (!filmid || !film)
        return res.status(404).send(`Film with id ${filmid} not found!`);

    films.splice(film, 1);

    fs.writeFile('data/films.json', JSON.stringify(films), (err) => {
        err ? res.status(500).json({ success: false }) : res.status(200).json({ success: true })
    });
});

router.post('/register', (req, res) => {
    if (!req.body.username || !req.body.password)
        return res.json({ error: 'No username or password.' });

    admin.auth().createUser({
        uid: toId(req.body.username),
        password: req.body.password,
        displayName: req.body.username,
        disabled: false,
        role: 0,
    })
        .then(userRecord => {
            res.status(200).json({ uid: userRecord.uid })
        })
        .catch(err => {
            res.status(500).json({ error: err })
        });
});

router.post('/login', (req, res) => {
    if (!req.body.username || !req.body.password)
        return res.json({ error: 'No username or password.' });

    admin.auth().getUser(toId(req.body.username))
        .then(userRecord => {
            res.status(200).json({ user: userRecord.toJSON() })
        })
        .catch(err => {
            res.status(500).json({ error: err })
        });
});

router.post('/addusermovie', (req, res) => {
    if (!req.body.username) return res.status(500).json({ error: 'No username' });
    if (!req.body.filmid || isNaN(Number(req.body.filmid))) return res.status(500).json({ error: 'Incorrect film id' });

    const getUser = () => userMovies[toId(req.body.username)];

    if (getUser() && getUser().movies && getUser().movies.includes(req.body.filmid))
        return res.status(500).json({ error: 'User already has movie added' });

    if (!getUser()) {
        userMovies[toId(req.body.username)] = {
            movies: [],
        }
    }

    getUser().movies.push(req.body.filmid);

    fs.writeFile('data/usermovies.json', JSON.stringify(userMovies), (err) => {
        err ? res.status(500).json({ success: false }) : res.status(200).json({ success: true });
    });
});

router.get('/usermovies/:username', (req, res) => {
    const userId = toId(req.params.username);
    const response = userMovies[userId] ? userMovies[userId].movies.map(m => films[m]) : [];

    res.json(response);

});

module.exports = router;
