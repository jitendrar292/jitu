
const prepareDivisionObject = recordlist => {

    let division = null;
    recordlist.find(record => {

        let newImage = record.newImage;;
        if (newImage.SK.startsWith('COMP#')
            && newImage.IsDivision === 1) {

            division = { offices: [] };
            division.eventName = record.eventName;
            division.divisionId = newImage.PK
            division.companyId = newImage.SK
            division.divisionName = newImage.CompanyName
            division.createdTime = newImage.CreatedDate
            division.updatedTime = newImage.UpdatedDate

            return true;
        }
        
    });

    return division;

};

module.exports = {
    prepareDivisionObject: prepareDivisionObject
};