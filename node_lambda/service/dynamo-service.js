const Config = require("../config/aws-config");
const AWS_Constants = require('../constant/aws-constants');

const docClient = new Config.AWS.DynamoDB.DocumentClient();

const fetchContact = async (contactId) => {

    let params = {
        TableName: AWS_Constants.TABLE_NAME,
        Key: {
            "PK": contactId,
            "SK": contactId
        }
    };

    return await scan(params)
}

const scan = params => {

    return new Promise((resolve, reject) => {
        docClient.get(params, function (err, data) {
            if (err) {
                reject(err)
            } else {
                resolve(data)
            }
        });
    });

}

module.exports = {
    fetchContact: fetchContact
}