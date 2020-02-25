// Load the module dependencies
const mongoose = require("mongoose");

mongoose.Promise = global.Promise;

const openDbConnection = async () => {
  let isConnected;
  if (isConnected) {
    console.log("using existing database connection.");
    return Promise.resolve();
  }

  let db = await mongoose
    .connect(process.env.DB_CONN, {
      useUnifiedTopology: true,
      useNewUrlParser: true,
      useCreateIndex: true
    });
    
    if(db)
      console.log("Connection to mongodb stablished");
    else
      console.log("There was a problem connecting to mongo");
    
  isConnected = db.connections[0].readyState;
};

module.exports = openDbConnection;
