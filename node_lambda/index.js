const dynamoMapper = require('./mapper/dynamo-mapper');
const service = require('./service/elastic-service');

exports.handler = async (event) =>  service.streamHandler(dynamoMapper(event))

