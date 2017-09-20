package jstack.eu.messagingApp.services;

import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.models.Message;
import jstack.eu.messagingApp.models.User;
import jstack.eu.messagingApp.repositories.ConversationRepository;
import jstack.eu.messagingApp.repositories.UserRepository;
import jstack.eu.messagingApp.repositories.impl.ConversationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    private ConversationRepository conversationRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ConversationDAO conversationDAO;

    @Autowired
    public Service(ConversationRepository conversationRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConversationDAO conversationDAO) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.conversationDAO = conversationDAO;
    }


    public Conversation getConversation(String id) {
        return conversationRepository.findOne(id);
    }

    public void editMessage(String conversationId, String id, String value) {
        Conversation conversation = conversationRepository.findOne(conversationId);
        Message message = conversation.getMessages().stream()
                .filter(msg -> msg.get_id().equals(id)).findFirst().orElse(null);
        if (message != null) {
            message.setMessage(value);
            conversationRepository.save(conversation);
        }
    }

    public Conversation createConversation(Conversation conversation) {
        return conversationRepository.insert(conversation);
    }

    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

    public User getUserByName(String value) {
        return userRepository.findByUsername(value);
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    public void saveConversation(Conversation conversation) {
        conversationRepository.save(conversation);
    }

    public boolean createUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            userRepository.insert(new User(username, bCryptPasswordEncoder.encode(password)));
            return true;
        }
        return false;
    }

    public void removeMessage(String conversationId, String messageId) {
        conversationDAO.deleteMessage(conversationId,messageId);
    }

    public void addMessage(String conversationId, Message message) {
        conversationDAO.addMessage(conversationId,message);
    }
}
