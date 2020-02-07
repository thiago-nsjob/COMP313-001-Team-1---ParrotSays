const serverless = require('serverless-http');
const express = require('express');
const app = express();
const bodyParser = require('body-parser');
var AWSXRay = require('aws-xray-sdk');

require("dotenv").config({ path: '/config/env/' + process.env.NODE_ENV + '.env' });

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


//requires the base route passing the root route
app.use(AWSXRay.express.openSegment('ParrotSays.ML.API'));

app.use(require('../routes/routes')("/api"));

app.use(AWSXRay.express.closeSegment());

module.exports.handle = serverless(app);