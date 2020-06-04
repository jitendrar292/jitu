const Config = require('../config/aws-config')
const constants = require('../constant/aws-constants')

const pushStream = event => {

    // Create publish parameters
    let params = {
        Message: JSON.stringify(event),
        TopicArn: constants.SNS_ARN
    };

    // Create promise and SNS service object
    let publishTextPromise = new Config.AWS.SNS().publish(params).promise();

    // Handle promise's fulfilled/rejected states
    publishTextPromise.then(function (data) {
        console.log(`Message ${params.Message} send sent to the topic ${params.TopicArn}`);
        console.log("MessageID is " + data.MessageId);
    }).catch(function (err) {
        console.log('error', err)

    });

}

module.exports = {
    pushStream: pushStream
}