package jstack.eu.messagingApp.models.DTO;

import java.io.Serializable;

public class EditMessageDTO implements Serializable{
    private String conversationId;
    private String id;
    private String value;

    public EditMessageDTO(String conversationId, String id, String value) {
        this.conversationId = conversationId;
        this.id = id;
        this.value = value;
    }

    public EditMessageDTO() {
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
