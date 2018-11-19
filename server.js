//INCLUDES DEPENDENCIES
var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');

//CONNECT TO MONGOBD
mongoose.connect('mongodb://abanuelo:Armandobn9@ds113648.mlab.com:13648/pife1-0', { useNewUrlParser: true });

//EXPRESS
var app = express();
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

//ROUTES
app.use('/api', require('./routes/api'));

//START SERVER
app.listen(3000);
console.log('Server is running on port 3000');
