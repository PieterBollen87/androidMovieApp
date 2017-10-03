// server.js

// BASE SETUP
// =============================================================================

// call the packages we need
const express    = require('express');        // call express
const app        = express();                 // define our app using express
const bodyParser = require('body-parser');
const routes = require('./router/index.js');


// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.urlencoded({ extended: true }));

app.use(bodyParser.json());

const port = process.env.PORT || 3000;        // set our port

// ROUTES FOR OUR API
// =============================================================================



// more routes for our API will happen here

// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', routes );
app.use('/public', express.static('public'));

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('Express app gestart op localhost:3000');
