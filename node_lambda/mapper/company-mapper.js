
const prepareComapnyObject = recordlist => {

    let company = null
    recordlist.find(record => {

        let newImage = record.newImage;;
        if (newImage.PK === newImage.SK
            && newImage.PK.includes("COMP#")
            && newImage.IsDivision === 0) {

            company = {
                offices: [],
                divisions: []
            };

            company.eventName = record.eventName;
            company.companyId = newImage.PK
            company.companyName = newImage.CompanyName
            company.createdTime = newImage.CreatedDate
            company.updatedTime = newImage.UpdatedDate  

            return true;
        }
        
    });

    return company;

};

module.exports = {
    prepareComapnyObject: prepareComapnyObject
};