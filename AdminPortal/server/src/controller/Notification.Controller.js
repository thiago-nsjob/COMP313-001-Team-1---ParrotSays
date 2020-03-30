const express = require("express");
const router = new express.Router();

const openDbConnection = require("../../config/mongoose");
const PostService = require("../service/PostService");
const Post = require("../model/Post");

const controllerRoute = "/notification";

const validatePost = data => {
    if (!"message" in data) throw new Error("parameter message not informed");
};

module.exports = rootRoute => {
    let baseRoute = rootRoute + controllerRoute;

    router.post(`${baseRoute}/notification`, async(req, res) => {
        try {
            const { message } = req.body;

            //TODO: call aws SNS and push message data

            return res.status(200).send({
                data: "Message sent"
            });
        } catch (error) {
            console.log(error, `Error on route ${baseRoute} method get/${postId} `);
            throw error;
        }
    });

    return router;
};