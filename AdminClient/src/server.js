const express = require('express');
const bodyParser = require('body-parser')
const path = require('path');
const app = express();
const testController = require('./api/controllers/TestController');


app.use(express.static(path.join(__dirname, 'build')));

//api base route
app.route('/api',testController);

//app base route
app.get('/', function (req, res) {
  res.sendFile(path.join(__dirname, 'build', 'index.html'));
});

app.listen(process.env.PORT || 3000);