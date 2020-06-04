const axios = require('axios');
const Config = require('./elastic-config');
const companyUpdateScript = require('../scripts/company/company-update')
const officeComapnyCreateScript = require('../scripts/office/office-company-create')
const officeComapnyUpdateScript = require('../scripts/office/office-company-update.json')
const officeDivisionCreateScript = require('../scripts/office/office-division-create.json')
const officeDivisionUpdateScript = require('../scripts/office/office-division-update.json')
const contactComapnyCreateScript = require('../scripts/contact/contact-company-create.json')
const contactComapnyUpdateScript = require('../scripts/contact/contact-company-update.json')
const contactDivisionCreateScript = require('../scripts/contact/contact-division-create.json')
const contactDivisionUpdateScript = require('../scripts/contact/contact-division-update.json')
const createDivisionScript = require('../scripts/division/division-create.json')
const updateDivisionScript = require('../scripts/division/division-update.json')


const saveCompany = async (company) => {

    let companyUrl = Config.CREATE_URL.concat(company.companyId.replace('#', '%23'));
    await axios.put(companyUrl, company)
};


const updateCompany = async (company) => {

    let companyUpdateUrl = Config.UPDATE_URL.concat(company.companyId.replace('#', '%23'));
    let updateCompany = companyUpdateScript;

    //initializing update script params
    updateCompany.script.params.companyName = company.companyName;
    updateCompany.script.params.updatedTime = company.updatedTime;

    await axios.post(companyUpdateUrl, updateCompany)

};

const saveOffice = async (office) => {

    let officeCreateUrl = Config.UPDATE_URL.concat(office.ultimateParentId.replace('#', '%23'));
    let createOfficeScript = null;

    if (office.ultimateParentId === office.companyId) {
        createOfficeScript = officeComapnyCreateScript;
        createOfficeScript.script.params.office = office;
    } else {
        createOfficeScript = officeDivisionCreateScript
        createOfficeScript.script.params.office = office;
        createOfficeScript.script.params.divisionId = office.companyId;
    }
    await axios.post(officeCreateUrl, createOfficeScript)
}


const updateOffice = async (office) => {

    let officeUpdateUrl = Config.UPDATE_URL.concat(office.ultimateParentId.replace('#', '%23'));
    let updateOfficeScript = null;
    if (office.ultimateParentId === office.companyId) {
        updateOfficeScript = officeComapnyUpdateScript;
        updateOfficeScript.script.params.officeId = office.officeId;
        updateOfficeScript.script.params.office = office;
    } else {
        updateOfficeScript = officeDivisionUpdateScript
        updateOfficeScript.script.params.divisionId = office.companyId;
        updateOfficeScript.script.params.office = office;

    }

    await axios.post(officeUpdateUrl, updateOfficeScript)
}

const saveContact = async (contact) => {

    let createContactUrl = Config.UPDATE_URL.concat(contact.ultimateParentId.replace('#', '%23'));
    let createContactScript = null;

    if (contact.ultimateParentId == contact.divisionId) {
        createContactScript = contactComapnyCreateScript;
        createContactScript.script.params.officeId = contact.officeId;
        createContactScript.script.params.contact = contact;
    } else {
        createContactScript = contactDivisionCreateScript
        createContactScript.script.params.contact = contact;
    }

    await axios.post(createContactUrl, createContactScript)
}

const updateContact = async (contact) => {

    let contactUpdateUrl = Config.UPDATE_URL.concat(contact.ultimateParentId.replace('#', '%23'));
    let contactUpdateScript = null;

    if (contact.ultimateParentId == contact.divisionId) {
        contactUpdateScript = contactComapnyUpdateScript;
        contactUpdateScript.script.params.officeId = contact.officeId;
        contactUpdateScript.script.params.contact = contact;
    }else{
        contactUpdateScript = contactDivisionUpdateScript
        contactUpdateScript.script.params.contact = contact;
    }

    await axios.post(contactUpdateUrl, contactUpdateScript)
}

const saveDivision = async (division) => {

    let createDivisionUrl = Config.UPDATE_URL.concat(division.companyId.replace('#', '%23'));
    let divisionCreateScript = createDivisionScript;

    //set division in script's params
    divisionCreateScript.script.params.division = division;
    await axios.post(createDivisionUrl, divisionCreateScript)
}

const updateDivision = async (division) => {

    let updateDivisionUrl = Config.UPDATE_URL.concat(division.companyId.replace('#', '%23'));
    let divisionUpdateScript = updateDivisionScript;

    //set script's params
    divisionUpdateScript.script.params.divisionId = division.divisionId
    divisionUpdateScript.script.params.divisionName = division.divisionName
    divisionUpdateScript.script.params.updatedTime = division.updatedTime

    await axios.post(updateDivisionUrl, divisionUpdateScript)
}

const fetchContactDataFromDynamo = async (contactId) => {

}


module.exports = {
    saveCompany: saveCompany,
    updateCompany: updateCompany,
    saveOffice: saveOffice,
    updateOffice: updateOffice,
    saveContact: saveContact,
    updateContact: updateContact,
    saveDivision: saveDivision,
    updateDivision: updateDivision,
    fetchContactDataFromDynamo: fetchContactDataFromDynamo
};