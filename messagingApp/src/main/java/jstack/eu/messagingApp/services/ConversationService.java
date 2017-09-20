package jstack.eu.messagingApp.services;

import com.google.gson.Gson;
import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.models.DTO.EditMessageDTO;
import jstack.eu.messagingApp.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ConversationService {
    private RestTemplate restTemplate = new RestTemplate();
    private String BASE_URL = "http://localhost:9003/api/v1/conversation";

    private Gson gson;

    @Autowired
    public ConversationService(Gson gson) {
        this.gson = gson;
    }

    public Conversation getConversation(String id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Conversation.class);
    }

    public List<Conversation> getAllConversations() {
        Conversation[] conversations = restTemplate.getForObject(BASE_URL, Conversation[].class);
        return Arrays.asList(conversations);
    }

    public Conversation createConversation(String value) {
        return restTemplate.postForEntity(BASE_URL, createEntity(new Conversation(value)), Conversation.class).getBody();
    }

    public void saveConversation(Conversation conversation) {
        restTemplate.put(BASE_URL, createEntity(conversation));
    }

    public Conversation editMessage(Conversation conversation, Message msg, String value) {
        restTemplate.put(BASE_URL + "/message", createEntity(new EditMessageDTO(conversation.get_id(), msg.get_id(), value)));
        return getConversation(conversation.get_id());
    }

    private HttpEntity<String> createEntity(Object obj) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(gson.toJson(obj), headers);
    }

    public void deleteMessage(String conversationId, String messageId) {
        restTemplate.delete(BASE_URL + "/" + conversationId + "/message/" + messageId);
    }

    public void addMessage(String conversationId, Message msg) {
        restTemplate.postForEntity(BASE_URL + "/" + conversationId + "/message", createEntity(msg), Conversation.class);
    }
}
