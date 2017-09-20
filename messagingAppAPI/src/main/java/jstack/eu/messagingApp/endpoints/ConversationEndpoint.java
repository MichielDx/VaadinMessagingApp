package jstack.eu.messagingApp.endpoints;

import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.models.DTO.EditMessageDTO;
import jstack.eu.messagingApp.models.Message;
import jstack.eu.messagingApp.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/conversation")
public class ConversationEndpoint {
    private Service service;


    @Autowired
    public ConversationEndpoint(Service service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Conversation conversation(@PathVariable(value = "id") String id) {
        return service.getConversation(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public Conversation[] conversations() {
        Conversation[] conversations = new Conversation[0];
        conversations = service.getAllConversations().toArray(conversations);
        return conversations;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Conversation createConversation(@RequestBody() Conversation conversation) {
        return service.createConversation(conversation);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void saveConversation(@RequestBody() Conversation conversation) {
        service.saveConversation(conversation);
    }

    @RequestMapping(value = "/message", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void editMessage(@RequestBody() EditMessageDTO editMessageDTO) {
        service.editMessage(editMessageDTO.getConversationId(), editMessageDTO.getId(), editMessageDTO.getValue());
    }

    @RequestMapping(value = "/{id}/message/{messageId}", method = RequestMethod.DELETE, produces = "application/json")
    public void deleteMessage(@PathVariable(value = "id") String conversationId, @PathVariable(value = "messageId") String messageId) {
        service.removeMessage(conversationId, messageId);
    }

    @RequestMapping(value = "/{id}/message", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void addMessage(@PathVariable(value = "id") String conversationId, @RequestBody Message message) {
        service.addMessage(conversationId, message);
    }
}
