'use strict'

const router = require('express').Router();
const fs = require('fs');
const multer = require('multer')
const toId = require('toid');
const GoogleAuth = require('google-auth-library');
const auth = new GoogleAuth;

const films = require('../data/films.json');
const userMovies = require('../data/usermovies.json');
const filmReviews = require('../data/filmReviews.json');
const admins = ['animereviewsxyz@gmail.com'];
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
    if (!req.body.token) return res.status(500).json({ error: 'No token' });
    if (!req.body.title) return res.status(500).json({ error: 'No film title' });
    if (!req.body.director) return res.status(500).json({ error: 'No film director' });
    if (!req.body.year) return res.status(500).json({ error: 'No film year' });
    if (!req.body.genre) return res.status(500).json({ error: 'No film genre' });
    if (!req.body.trailer) return res.status(500).json({ error: 'No film trailer' });
    if (Object.keys(films).find(f => toId(films[f].title) === toId(req.body.title)))
        return res.status(500).json({ error: 'Film already exists' });

    const client = new auth.OAuth2(serviceAccount.client_id, '', '');
    client.verifyIdToken(
        req.body.token,
        serviceAccount.client_id,
        (e, login) => {
            if (!login) return res.status(500).send('Login session expired');

            const payload = login.getPayload();
            const userid = payload['sub'];
            if (!userid || !userid.length) return res.status(401);

            const userEmail = payload.email;
            if (!admins.includes(userEmail)) return res.status(401);

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

router.post('/addusermovie', (req, res) => {
    if (!req.body.filmid || isNaN(Number(req.body.filmid))) return res.status(500).json({ error: 'Incorrect film id' });
    if (!req.body.token) return res.status(500).send('No token');

    const client = new auth.OAuth2(serviceAccount.client_id, '', '');
    client.verifyIdToken(
        req.body.token,
        serviceAccount.client_id,
        (e, login) => {
            if (!login) return res.status(500).send('Login session expired');

            const payload = login.getPayload();
            const userid = payload['sub'];
            if (!userid || !userid.length) return res.status(401);

            const userEmail = payload.email;
            const getUser = () => userMovies[toId(userEmail)];

            if (getUser() && getUser().movies && getUser().movies.includes(req.body.filmid))
                return res.status(500).send('User already has movie added');

            if (!getUser()) {
                userMovies[toId(userEmail)] = {
                    movies: [],
                }
            }

            getUser().movies.push(req.body.filmid);

            fs.writeFile('data/usermovies.json', JSON.stringify(userMovies), (err) => {
                err ? res.status(500).send(err) : res.status(200).send('Movie added to personal list!');
            });
        });
});

router.get('/usermovies/:username', (req, res) => {
    const userId = toId(req.params.username);
    const response = userMovies[userId] ? userMovies[userId].movies.map(m => films[m - 1]) : [];

    res.json(response);

});

router.post('/addfilmreview', (req, res) => {
    if (!req.body.token) return res.status(500).json('No token');
    if (!req.body.filmid) return res.status(500).json('No film id');
    if (!req.body.rating) return res.status(500).json('No film rating');
    if (!req.body.description) return res.status(500).json('no review description');

    const client = new auth.OAuth2(serviceAccount.client_id, '', '');
    client.verifyIdToken(
        req.body.token,
        serviceAccount.client_id,
        (e, login) => {
            if (!login) return res.status(500).send('Login session expired');

            const payload = login.getPayload();
            const userid = payload['sub'];
            if (!userid || !userid.length) return res.status(401);

            const userEmail = payload.email;
            const getMovieReviews = () => filmReviews[req.body.filmid];

            if (getMovieReviews() && getMovieReviews().reviews && getMovieReviews().reviews.find(r => r.user === toId(userEmail)))
                return res.status(500).send('User already has made a review for this movie');

            if (!getMovieReviews()) {
                filmReviews[req.body.filmid] = {
                    reviews: [],
                }
            }

            getMovieReviews().reviews.push({
                user: toId(userEmail), userName: payload.name, avatar: payload.picture, rating: req.body.rating, description: req.body.description
            });

            fs.writeFile('data/filmreviews.json', JSON.stringify(filmReviews), (err) => {
                err ? res.status(500).send(err) : res.status(200).send('Movie review added!');
            });
        });

    router.get('/filmsreviews/:filmid', (req, res) => {
        const filmId = toId(req.params.filmid);
        const response = filmReviews[filmId] ? filmReviews[filmId] : [];

        res.json(response);

    });
});

module.exports = router;
