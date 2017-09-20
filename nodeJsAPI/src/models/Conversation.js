const DBHelper = require('helpers/DBHelper');
const ConversationSchema = require('models/schemas/ConversationSchema');

module.exports = DBHelper.model('conversation', ConversationSchema);