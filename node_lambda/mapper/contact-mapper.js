

const prepareContactObject = recordlist => {

    let contact = null;
    recordlist.find(record => {

        let newImage = record.newImage;
        if (newImage.PK === newImage.SK
            && newImage.PK.includes("CONTACT#")) {

            contact = { jobs: [] };
            contact.eventName = record.eventName;
            contact.contactId = newImage.PK;
            contact.ultimateParentId = newImage.UltimateParentCompanyId;
            contact.divisionId = newImage.DivisionId
            contact.firstName = newImage.FirstName;
            contact.lastName = newImage.LastName;
            contact.fullName = newImage.FullName;
            contact.officeId = newImage.AssociatedOfficeId;
            contact.emailId = newImage.CommunicationChannelDBO.Email[0].EmailId;
            contact.emailUsage = newImage.CommunicationChannelDBO.Email[0].EmailIdUsage;
            contact.phoneNumber = newImage.CommunicationChannelDBO.Phone[0].PhoneNumber;
            contact.createdTime = newImage.CreatedDate
            contact.updatedTime = newImage.UpdatedDate

            return true;
        }
    });

    return contact;
};

const searchForContactOfficeObject = recordList => {

    let contactId = null
    recordList.find(record => {

        let newImage = record.newImage;
        if (newImage.Id != undefined
            && newImage.Id.startsWith('OFC#')) {
            contactId = newImage.PK;

            return true
        }

    });

    return contactId;
}

const prepareContactFromDynamo = contactDBO => {

    let contact = { jobs: [] };
    let newImage = contactDBO.Item;
    contact.eventName = 'INSERT';
    contact.contactId = newImage.PK;
    contact.ultimateParentId = newImage.UltimateParentCompanyId;
    contact.divisionId = newImage.DivisionId
    contact.firstName = newImage.FirstName;
    contact.lastName = newImage.LastName;
    contact.fullName = newImage.FullName;
    contact.officeId = newImage.AssociatedOfficeId;
    contact.emailId = newImage.CommunicationChannelDBO.Email[0].EmailId;
    contact.emailUsage = newImage.CommunicationChannelDBO.Email[0].EmailIdUsage;
    contact.phoneNumber = newImage.CommunicationChannelDBO.Phone[0].PhoneNumber;
    contact.createdTime = newImage.CreatedDate
    contact.updatedTime = newImage.UpdatedDate

    return contact;
}

module.exports = {
    prepareContactObject: prepareContactObject,
    searchForContactOfficeObject: searchForContactOfficeObject,
    prepareContactFromDynamo: prepareContactFromDynamo
};