const ResponseHelper = require('helpers/ResponseHelper');

const UserService = require('services/UserService');
const UserDTO = require('models/UserDTO');
const ValidationHelper = require('helpers/ValidationHelper');

module.exports = (App) => {

    App.get('/api/v1/user/:username', (req, res) => {
        ResponseHelper.promiseResponseHandler(req, res, UserService.getByUsername(req.params.username));
    });

    App.post('/api/v1/user/login', ValidationHelper.modelValidator(UserDTO), (req, res) => {
        UserService.login(req.body, req, res);
    });

    App.post('/api/v1/user', (req, res) => {
        UserService.create(req.body, res);
    });

};