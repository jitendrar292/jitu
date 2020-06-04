const dynamoConverters = require('dynamo-converters');

const mapper = (event) => {

    let records = [];
    event.Records.forEach((record) => {

        let recordInfo = {};
        recordInfo.eventName = record.eventName;
        recordInfo.newImage = dynamoConverters.itemToData(record.dynamodb.NewImage);
        records.push(recordInfo);
    });

    return records;

};

module.exports = mapper;