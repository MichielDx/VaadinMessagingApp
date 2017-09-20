const ResponseHelper = require('helpers/ResponseHelper');

const ConversationService = require('services/ConversationService');
const Conversation = require('models/Conversation');
const Message = require('models/Message');
const ValidationHelper = require('helpers/ValidationHelper');

module.exports = (App) => {
    App.get('/api/v1/conversation/:id', (req, res) => {
        ResponseHelper.promiseResponseHandler(req, res, ConversationService.getConversation(req.params.id));
    });

    App.get('/api/v1/conversation', (req, res) => {
        ResponseHelper.promiseResponseHandler(req, res, ConversationService.getAllConversation());
    });

    App.post('/api/v1/conversation', ValidationHelper.modelValidator(Conversation), (req, res) => {
        ResponseHelper.promiseResponseHandler(req, res, ConversationService.create(req.body));

    });

    App.put('/api/v1/conversation', ValidationHelper.modelValidator(Conversation), (req, res) => {
        ResponseHelper.promiseResponseHandler(req, res, ConversationService.update(req.body));
    });

    App.put('/api/v1/conversation/message', ValidationHelper.modelValidator(Conversation), (req, res) => {
        ResponseHelper.promiseResponseHandler(req, res, ConversationService.updateMessage(req.body));
    });

    App.delete('/api/v1/conversation/:conversationId/message/:messageId', (req, res) => {
        ResponseHelper.promiseResponseHandler(req, res, ConversationService.removeMessage(req.params.conversationId,req.params.messageId));
    });

    App.post('/api/v1/conversation/:conversationId/message', ValidationHelper.modelValidator(Message), (req, res) => {
        ResponseHelper.promiseResponseHandler(req, res, ConversationService.addMessage(req.params.conversationId,req.body));
    });
};