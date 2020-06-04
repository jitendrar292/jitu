

const prepareOfficeObject = recordList => {

    let office = null;
    recordList.find(record => {

        let newImage = record.newImage;
        if (newImage.PK.includes('COMP#')
            && newImage.StandardizedAddress !== undefined
            && newImage.SK.startsWith('OFC#')) {

            office = {
                address: {},
                contacts: []
            };

            office.eventName = record.eventName;
            office.officeId = newImage.SK;
            office.companyId = newImage.PK;
            office.ultimateParentId = newImage.UltimateParentCompanyId;
            office.createdTime = newImage.CreatedDate
            office.updatedTime = newImage.UpdatedDate
            office.address.standardizedAddress = newImage.StandardizedAddress;
            office.address.city = newImage.CommunicationChannel.Address[0].City;
            office.address.state = newImage.CommunicationChannel.Address[0].State;
            office.address.postalCode = newImage.CommunicationChannel.Address[0].PostalCode;
            office.address.addressLine1 = newImage.CommunicationChannel.Address[0].AddressLine1;

            return true;
        }

    });


    return office;

};

module.exports = {
    prepareOfficeObject: prepareOfficeObject
};