const DBHelper = require('helpers/DBHelper');
const EditMessageSchema = require('models/schemas/EditMessageDTOSchema');

module.exports = DBHelper.model('editmessage', EditMessageSchema);