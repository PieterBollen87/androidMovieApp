'use strict'

const router = require('express').Router();
const fs = require('fs');
const bcrypt = require('bcrypt-nodejs');
const jwt = require('jsonwebtoken');
const toId = require('toid');

const films = require('../data/films.json');
const users = require('../data/users.json');


router.get('/films', (req, res) => {
    res.json(films);
});

router.post('/addfilm', (req, res) => {
    if (!req.body.title) return res.status(500).json({ error: 'No film title' });
    if (!req.body.director) return res.status(500).json({ error: 'No film director' });
    if (!req.body.year) return res.status(500).json({ error: 'No film year' });
    if (Object.keys(films).find(f => toId(films[f]).title) === toId(req.body.title))
        return res.status(500).json({ error: 'Film already exists' });

    const film = {
        id: films.length + 1,
        title: req.body.title,
        director: req.body.director,
        year: req.body.year,
    };

    films.push(film);

    fs.writeFile('data/films.json', JSON.stringify(films), (err) => {
        err ? res.status(500).json({ success: false }) : res.sendStatus(200);
    });
});

router.delete('/deletefilm/:id', (req, res) => {
    const filmid = req.params.id;
    const film = Object.keys(films).find(f => Number(films[f].id) === Number(filmid));

    if (!filmid || !film)
        return res.status(404).send(`Film with id ${filmid} not found!`);

    films.splice(film, 1);

    fs.writeFile('data/films.json', JSON.stringify(films), (err) => {
        err ? res.status(500).json({ success: false }) : res.sendStatus(200)
    });
});

router.post('/register', (req, res) => {
    if (!req.body.username || !req.body.password)
        return res.json({ error: 'No username or password.' });
    if (req.body.username.length > 19 || req.body.password.length > 150)
        return res.json({ error: 'Username or password is too long.' });

    if (users[toId(req.body.username)])
        return res.json({ error: 'Someone has already registered this username.' });

    const username = req.body.username;

    bcrypt.genSalt(10, (err, salt) => {
        if (err) return res.json({ error: 'Salt failed.' });
        bcrypt.hash(req.body.password, salt, null, (err, hash) => {
            if (err) return res.json({ error: 'Hash failed.' });

            users[toId(req.body.username)] = {
                username: username,
                password: hash,
            };

            fs.writeFile('data/users.json', JSON.stringify(users), (err) => {
                err ? res.status(500).json({ success: false })
                    : res.status(200).json(
                        jwt.sign({ username }, 'jwt secret token! can be anything', { expiresIn: '1d' }));
            });

        });
    });
});

router.post('/login', (req, res) => {
    if (!req.body.username || !req.body.password)
        return res.json({ error: 'No username or password.' });
    if (req.body.username.length > 19 || req.body.password.length > 150)
        return res.json({ error: 'Username or password is too long.' });

    const user = users[toId(req.body.username)];
    if (!user) return res.json({ error: 'Username is not registered.' });

    const hash = user.password;
    const username = user.username;

    bcrypt.compare(req.body.password, hash, (err, isMatch) => {
        if (err) return res.json({ error: 'Compare failed.' });
        if (!isMatch) return res.json({ error: 'Invalid password.' });
        const token = jwt.sign({ username }, 'jwt secret token! can be anything', { expiresIn: '1d' });
        res.json({ token });
    });
});

module.exports = router;