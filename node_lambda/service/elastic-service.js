const apiClient = require('../config/client');
const companyMapper = require('../mapper/company-mapper')
const divisionMapper = require('../mapper/division-mapper')
const officeMapper = require('../mapper/office-mapper')
const contactMapper = require('../mapper/contact-mapper')
const StreamConst = require('../constant/stream-constants')
const dynamoService = require('./dynamo-service')

const streamHandler = async (recordList) => {

    const company = companyMapper.prepareComapnyObject(recordList);
    const division = divisionMapper.prepareDivisionObject(recordList);
    const office = officeMapper.prepareOfficeObject(recordList);
    const contact = contactMapper.prepareContactObject(recordList);

    if (company) {
        await saveOrUpdateCompany(company);
    }

    if (division) {
        await saveOrUpdateDivision(division);
    }

    if (office) {
        await saveOrUpdateOffice(office);
    }

    if (contact) {
        await saveOrUpdateContact(contact);
    } else {
        const contactId = contactMapper.searchForContactOfficeObject(recordList);
        if (contactId) {
            saveOrUpdateContact(contactMapper.prepareContactFromDynamo(await dynamoService.fetchContact(contactId)));
        }
    }

};

const saveOrUpdateCompany = async (company) => {

    if (company.eventName === StreamConst.STREAM_INSERT) {
        company.eventName = undefined;
        await apiClient.saveCompany(company);
    } else if (company.eventName === StreamConst.STREAM_MODIFY) {
        company.eventName = undefined;
        await apiClient.updateCompany(company);
    }

};


const saveOrUpdateOffice = async (office) => {

    if (office.eventName === StreamConst.STREAM_INSERT) {
        office.eventName = undefined;
        await apiClient.saveOffice(office);

    } else if (office.eventName === StreamConst.STREAM_MODIFY) {
        office.eventName = undefined;
        await apiClient.updateOffice(office);
    }

};

const saveOrUpdateContact = async (contact) => {

    if (contact.eventName === StreamConst.STREAM_INSERT) {
        contact.eventName = undefined;
        await apiClient.saveContact(contact);

    } else if (contact.eventName === StreamConst.STREAM_MODIFY) {
        contact.eventName = undefined;
        await apiClient.updateContact(contact);
    }


};

const saveOrUpdateDivision = async (division) => {

    if (division.eventName === StreamConst.STREAM_INSERT) {
        division.eventName = undefined;
        await apiClient.saveDivision(division);

    } else if (division.eventName === StreamConst.STREAM_MODIFY) {
        division.eventName = undefined;
        await apiClient.updateDivision(division);
    }

};

module.exports = {
    streamHandler: streamHandler
};