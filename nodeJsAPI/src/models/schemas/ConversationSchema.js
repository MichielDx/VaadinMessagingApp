const userschema = require('models/schemas/UserSchema');

module.exports = R.mergeAll(
    {
        _class: String,
        name: String,
        messages: [{
            _id: String,
            message: String,
            user: {
                _id: String,
                username: String,
                password: String
            }
        }]
    }
);