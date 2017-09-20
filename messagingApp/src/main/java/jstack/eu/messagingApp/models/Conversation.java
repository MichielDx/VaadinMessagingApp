package jstack.eu.messagingApp.models;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    @Id
    private String _id;

    private String name;

    private List<Message> messages;

    public Conversation() {
        messages = new ArrayList<>();
    }

    public Conversation(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String get_id() {
        return _id;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.removeIf(msg -> msg.get_id().equals(message.get_id()));
        //messages.remove(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
