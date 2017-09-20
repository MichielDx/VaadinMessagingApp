package jstack.eu.messagingApp.UI;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.services.ConversationService;
import jstack.eu.messagingApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = "conversation")
public class ConversationView extends GridLayout implements View {
    private Conversation conversation = new Conversation();

    private UserService userService;
    private ConversationService conversationService;
    private MessageFormView messageFormView;

    @Autowired
    public ConversationView(UserService userService, ConversationService conversationService, MessageFormView messageFormView) {
        this.userService = userService;
        this.conversationService = conversationService;
        this.messageFormView = messageFormView;
    }

    @PostConstruct
    public void init() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.removeAllComponents();
        if (event.getParameters() != null) {
            // split at "/", add each part as a label
            String[] msgs = event.getParameters().split("/");
            for (String msg : msgs) {
                this.conversation = conversationService.getConversation(msg);
                this.setRows(2);
                this.setColumns(2);
                //big box for messages
                VerticalLayout messageArea = new VerticalLayout();
                messageArea.setStyleName("messageArea");

                messageFormView.init(messageArea, conversation);
                messageFormView.enter();

                VerticalLayout verticalLayout = new VerticalLayout();

                if (conversation != null) {
                    Label conversationNameLabel = new Label("<h2><b>"+conversation.getName()+"</b></h2>",ContentMode.HTML);
                    conversationNameLabel.setStyleName("conversationName");
                    verticalLayout.addComponent(conversationNameLabel);
                    conversation.getMessages().forEach(message -> messageFormView.addMessage(message, false));
                }


                verticalLayout.addComponent(messageArea);
                this.addComponent(messageFormView, 0, 0, 0, 0);
                this.addComponent(verticalLayout, 1, 0, 1, 0);
            }
        }
    }
}
