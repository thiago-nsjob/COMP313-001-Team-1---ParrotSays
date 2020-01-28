
const express = require('express');
const router = express.Router();

//import services here


// Home page route.
router.get('/', function (req, res) {
  res.send('test api base');
})

// About page route.
router.get('/test', function (req, res) {
  res.send('test api test');
})

module.exports = router;