const express = require("express");

const router = express.Router();
const path = require("path");
const normalizedPath = path.join(__dirname, "../controller");


module.exports = (baseRoute) =>{
  require("fs")
    .readdirSync(normalizedPath)
    .forEach(file => {
      console.log("looking for controllers in ->" + normalizedPath);
      console.log("tyring to add controller file->" + file);
      router.use(require(path.join(normalizedPath, file))(baseRoute));
    })

    return router;
};
