const index = require('./index')
const dynamoStream = require('./json/create-all.json')

index.handler(dynamoStream)