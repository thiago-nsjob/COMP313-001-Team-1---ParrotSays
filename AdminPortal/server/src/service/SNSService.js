module.exports = {

    async SendMessage(message) {

        const AWS = require('aws-sdk');
        const credentials = new AWS.SharedIniFileCredentials({profile: 'dev_user'});
        const sns = new AWS.SNS({credentials: credentials, region: 'ca-central-1'});

        // Create publish parameters
        var params = {
            Message: message,
            TopicArn: 'arn:aws:sns:ca-central-1:113508044065:ParrotSaysNotification'
        };

        // Publish to SNS service
        sns.publish(params, function(err, data) {
            if (err) console.log(err, err.stack); 
            else console.log(`Message ${params.Message} send sent to the topic ${params.TopicArn}`);
        });        

    }
}
