const service = require('./service/sns-service')

exports.handler = async (event) =>  service.pushStream(event)