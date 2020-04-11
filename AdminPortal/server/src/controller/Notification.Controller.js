const express = require("express");
const router = new express.Router();
const SNSService = require("../service/SNSService");


const controllerRoute = "/notification";

const validatePost = data => {
    if (!"message" in data) throw new Error("parameter message not informed");
};

module.exports = rootRoute => {
    let baseRoute = rootRoute + controllerRoute;

    router.post(`${baseRoute}`, async(req, res) => {
        try {
            const { message } = req.body;

            await SNSService.SendMessage(message);

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