//DEPENDENCIES
var express = require('express');
var router = express.Router();

//GET MODELS
var Status = require('../models/status');

//ROUTES
Status.methods(['get', 'post', 'put', 'delete']);
Status.register(router, '/status');

//RETURN ROUTER
module.exports = router;

