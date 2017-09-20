/**
 * Created by vanraar on 23/06/17.
 */
const Mongoose = require('mongoose');
Mongoose.Promise = global.Promise;
Mongoose.set('debug', true);
Mongoose.connect('mongodb://localhost/messagingApp', {
    useMongoClient: true
});

const ObjectId = require('mongoose').Types.ObjectId;

const findByUsername = (model) => (username) => model.findOne({'username': username});
const findAll = (model) => () => model.find({}).then((list) => list.map((obj) => obj.toObject()));
const getById = (model) => (id) => model.findOne({'_id': id}).then((obj) => obj.toObject());
const getByProperties = (model) => (where) => model.find(where).then((list) => list.map((obj) => obj.toObject()));
const create = (model) => (raw) => new model(raw).save().then((obj) => obj.toObject());
const update = (model) => (id, raw) => model.findOneAndUpdate({_id: id}, raw, {"new": true});

const removeMessage = (model) => (convId, msgId) => model.collection.update({'_id': new ObjectId(convId)}, {"$pull": {"messages": {"_id": new ObjectId(msgId)}}});
const addMessage = (model) => (convId, msg) => model.collection.update({'_id': new ObjectId(convId)}, {"$push" : { "messages" : msg}});

module.exports = (model) => {
    return {
        findByUsername: findByUsername(model),
        findAll: findAll(model),
        create: create(model),
        getById: getById(model),
        getByProperties: getByProperties(model),
        update: update(model),

        removeMessage: removeMessage(model),
        addMessage: addMessage(model),
    }
};