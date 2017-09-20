const DBHelper = require('helpers/DBHelper');
const MessageSchema = require('models/schemas/MessageSchema');

module.exports = DBHelper.model('message', MessageSchema);