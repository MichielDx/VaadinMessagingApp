const User = require('models/User');
const UserRepository = require('repositories/GenericRepository')(User);
const bcrypt = require('bcrypt');
const ResponseHelper = require('helpers/ResponseHelper');

const getByUsername = (username) => {
    return UserRepository.findByUsername(username).then((obj) => obj.toObject()).then(asFullUser);
};

const login = (userDTO, req, res) => {
    UserRepository.findByUsername(userDTO.username)
        .then(function (obj) {
            if (obj) return obj.toObject();
            throw new Error("User does not exist");
        })
        .then(function (user) {
            bcrypt.compare(userDTO.password, user.password).then((result) => ResponseHelper.successResponse(req, res, result))
        })
        .catch(function (err) {
            console.log(err);
            ResponseHelper.errorResponse(req, res, err)
        })
};

const create = (userDTO, res) => {
    UserRepository.findByUsername(userDTO.username)
        .then(function (obj) {
            if (obj) throw new Error("User already exists");
        })
        .then(function () {
            bcrypt.hash(userDTO.password, 10).then(function(hash){
                userDTO.password = hash;
                UserRepository.create(userDTO).then((user)=> res.status(200).send(asFullUser(user)));
            });
        })
        .catch(function (err) {
            console.log(err);
        });
};

const asFullUser = (user) => R.omit(['password'], R.clone(user));

module.exports = {getByUsername, login, create, asFullUser};