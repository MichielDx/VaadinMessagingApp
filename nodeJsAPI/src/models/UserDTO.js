const DBHelper = require('helpers/DBHelper');
const UserDTOSchema = require('models/schemas/UserDTOSchema');

module.exports = DBHelper.model('userDTO', UserDTOSchema);