const mongoose = require("mongoose");
const PostSchema = new mongoose.Schema(
  {
    origin: { type: String }, //Twitter, Facebook, etc
    createdBy: { type: String },
    text: { type: String },
    createdAt: { type: Date },
    hashTags: Array,
    comments: Array,
    isHazard: Boolean,
    postUrl: String,
    mlAnalyse: {}
  },
  { timestamps: true }
);

 module.exports = User =  mongoose.models.post || mongoose.model("post", PostSchema);
 
 
