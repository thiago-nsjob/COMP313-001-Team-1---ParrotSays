const express = require('express');
const bodyParser = require('body-parser')
const path = require('path');
const app = express();
const routes = require('./api/routes/routes');
const port = process.env.PORT || 3000;

app.use(express.static(path.join(__dirname, '../build')));

//app base route
app.get('/', function (req, res) {
  res.sendFile(path.join(__dirname, 'build', 'index.html'));
});

//api base route
app.use('/api',routes);

app.listen(port);
console.log(`server is up at ${port}`)