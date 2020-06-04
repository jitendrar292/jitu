const BASE_URL = 'http://localhost:9200';
const INDEX = 'client'
const CREATE_TYPE = '_doc'
const UPDATE_TYPE = '_update'
const SEPRATOR = '/';
const CREATE_URL = BASE_URL.concat(SEPRATOR).concat(INDEX).concat(SEPRATOR).concat(CREATE_TYPE).concat(SEPRATOR)
const UPDATE_URL = BASE_URL.concat(SEPRATOR).concat(INDEX).concat(SEPRATOR).concat(UPDATE_TYPE).concat(SEPRATOR)

module.exports = {
    BASE_URL: BASE_URL,
    INDEX: INDEX,
    CREATE_TYPE: CREATE_TYPE,
    UPDATE_TYPE: UPDATE_TYPE,
    SEPRATOR: SEPRATOR,
    CREATE_URL: CREATE_URL,
    UPDATE_URL: UPDATE_URL
}