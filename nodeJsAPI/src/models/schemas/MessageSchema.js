module.exports = R.mergeAll(
    {
        _id: String,
        message: String,
        user: {
            _id: String,
            username: String
        }
    }
);