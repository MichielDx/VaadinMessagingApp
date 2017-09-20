const userschema = require('models/schemas/UserSchema');

module.exports = R.mergeAll(
    {
        conversationId: String,
        value: String,
        id: String
    }
);