var router = require('express').Router();
var films = require('../data/films.json');
var fs  = require('fs');
router.get('/films', function (request, response) {
    response.json(films);
});

router.post('/addfilm', function (request, response) {
    var lastId = 0;

    films.forEach(function (film) {
        if (film.id > lastId) {
            lastId = film.id;
        }
    });
    var film = {
        id: lastId+1,
        title:request.body.title,
        auteur:request.body.auteur,
        jaar:request.body.jaar

    };
    films.push(film);
    fs.writeFile('data/films.json', JSON.stringify(films), function (err) {
        if(err){
            response.json({success:false});
        }else{
            response.json(films);
        }
    });
});
router.delete('/deletefilm/:id', function(request, response){
    films.forEach(function (film) {
        if (film.id == request.params.id) {
            films.splice(films.indexOf(film), 1);

            fs.writeFile('data/films.json', JSON.stringify(films), function (err) {
                if(err){
                    response.json({success:false});
                }else{
                    response.json(films);

                }
            });
        }
    });
});

module.exports = router;