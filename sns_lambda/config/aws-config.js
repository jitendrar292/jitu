const AWS = require('aws-sdk');
const AWS_Constants = require('../constant/aws-constants')

AWS.config.update({
  region: AWS_Constants.REGION,
  accessKeyId: AWS_Constants.ACCESS_KEY,
  secretAccessKey: AWS_Constants.SECRET_ACCESS_KEY
});

module.exports = {
    AWS: AWS
}