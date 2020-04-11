module.exports = {
    async SendMessage(message) {
        const AWS = require("aws-sdk");
        const sns = new AWS.SNS({ region: "us-east-1" });


        let params = {
            Message: message,
            TopicArn: "arn:aws:sns:us-east-1:113508044065:ParrotSaysNotification"
        };

        try {
            let result = await sns.publish(params).promise();
            console.log(
                `Message ${params.Message} send sent to the topic ${params.TopicArn}`
            );
        } catch (err) {
            console.log(err, err.stack);
        }
    }
};