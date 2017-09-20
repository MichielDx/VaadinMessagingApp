const Conversation = require('models/Conversation');
const ConversationRepository = require('repositories/GenericRepository')(Conversation);

const UserService = require('services/UserService');
const Mongoose = require('mongoose');
const ObjectId = require('mongoose').Types.ObjectId;

const getConversation = ConversationRepository.getById;
const getAllConversation = ConversationRepository.findAll;
const create = (conversation, requestor) => {
    return ConversationRepository.create(conversation);
};

const update = (conversation, requestor) => {
    return ConversationRepository.update(conversation._id, conversation);

};

const updateMessage = (editMessage, requestor) => {
    let conversation = ConversationRepository.getById(editMessage.conversationId);
    return conversation
        .then(function (conv) {
            conv.messages.find(msg => msg._id === editMessage.id).message = editMessage.value;
            return ConversationRepository.update(conv._id, conv);
        })
        .catch(function (err) {
            console.log(err);
            return null;
        });

};

const removeMessage = (conversationId, messageId) => {
    return ConversationRepository.removeMessage(conversationId, messageId);
};

const addMessage = (conversationId, msg) => {
    msg._id = new ObjectId(msg._id);
    return ConversationRepository.addMessage(conversationId, msg);
};

module.exports = {getConversation, getAllConversation, create, update, updateMessage, removeMessage, addMessage};
