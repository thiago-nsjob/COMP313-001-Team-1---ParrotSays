const express = require("express");
const router = new express.Router();

const openDbConnection = require("../../config/mongoose");
const PostService = require("../service/PostService");
const Post = require("../model/Post");

const controllerRoute = "/post";

const validatePost = data => {
  if (!"origin" in data) throw new Error("parameter origin not informed");
  if (!"createdBy" in data) throw new Error("parameter createdBy not informed");
  if (!"text" in data) throw new Error("parameter text not informed");
  if (!"createdAt" in data) throw new Error("parameter createdAt not informed");
  if (!"comments" in data) throw new Error("parameter comments not informed");
  if (!"isHazard" in data) throw new Error("parameter isHazard not informed");
  if (!"mlAnalyse" in data) throw new Error("parameter mlAnalyse not informed");
  if (!"postUrl" in data) throw new Error("parameter postUrl not informed");
};

module.exports = rootRoute => {
  let baseRoute = rootRoute + controllerRoute;

  router.post(`${baseRoute}`, async (req, res) => {
    try {
      await openDbConnection();
      validatePost(req.body);
      let newPost = new Post(req.body);
      return res.status(200).send(await newPost.save());
    } catch (error) {
      console.log(error, `Error on route ${baseRoute} method post `);
      throw error;
    }
  });

  router.get(`${baseRoute}`, async (req, res) => {
    try {
      await openDbConnection();
      const allProducts = await PostService.getAllPosts();
      if (allProducts) {
        return res.status(200).send({
          data: allProducts
        });
      }
    } catch (error) {
      console.log(error, `Error on route ${baseRoute} method get `);
      throw error;
    }
  });

  router.get(`${baseRoute}/:postId`, async (req, res) => {
    try {
      await openDbConnection();
      const { postId } = req.params;
      return res.status(200).send({
        data: await PostService.getPostById(postId)
      });
    } catch (error) {
      console.log(error, `Error on route ${baseRoute} method get/${postId} `);
      throw error;
    }
  });

  return router;
};
