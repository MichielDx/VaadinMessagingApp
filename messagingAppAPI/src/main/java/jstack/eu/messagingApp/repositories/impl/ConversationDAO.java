package jstack.eu.messagingApp.repositories.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.models.Message;
import jstack.eu.messagingApp.repositories.ConversationRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ConversationDAO {
    private final MongoOperations mongoTemplate;

    @Autowired
    public ConversationDAO(MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void deleteMessage(String conversationId, String messageId) {

        Query findQuery =
                Query.query(Criteria.where("_id").is(conversationId));

        DBObject pullUpdate =
                BasicDBObjectBuilder.start().add(
                        "_id",
                        BasicDBObjectBuilder.start()
                                .add("$eq",
                                        new ObjectId(messageId)).get()).get();

        Update update = new Update().pull("messages", pullUpdate);

        mongoTemplate.updateMulti(findQuery, update, Conversation.class);


    }

    public void addMessage(String conversationId, Message message){
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(conversationId)),
                new Update().push("messages", message), Conversation.class);
    }
}
