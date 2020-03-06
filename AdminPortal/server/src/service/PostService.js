const Post = require("../model/Post");

module.exports = {
  async createPost(post) {

    let result = await  Post.save(post);
    if (result) {
      return {
        data: post,
        message: "Product successfully created!"
      };
    }
    return "Error creating new product";
  },

  async getAllPosts() {
    let posts = await Post.find(
       {isHazard : true}
    );
    if (posts) return posts;
    return "Error fetching products from db";
  },

  async getPostById(postId) {
    let post = await Post.findById(postId);
    if (post) return post;
    return "Error fetching product from db";
  }
};
