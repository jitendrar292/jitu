const createAllJson = require('./json/create-all.json');
const modifyAllJson = require('./json/modify-all.json');
const index = require('./index');
const dynamoService = require('./service/dynamo-service')

index.handler(createAllJson)
//dynamoService.fetchContact('CONTACT#448f794f-ed50-47ff-9982-40733ef23ed9')


