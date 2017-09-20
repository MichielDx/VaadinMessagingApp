package jstack.eu.messagingApp.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

public class Message implements Serializable {
    @Id
    private String _id;
    private String message;
    private User user;

    public Message() {
        _id = new ObjectId().toHexString();
    }

    public Message(String message, User user) {
        this();
        this.message = message;
        this.user = user;
    }

    public String get_id() {
        return _id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        return this._id.equals(((Message)obj).get_id());
    }
}
