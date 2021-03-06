package jstack.eu.messagingApp.UI;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.models.Message;
import jstack.eu.messagingApp.models.User;
import jstack.eu.messagingApp.services.ConversationService;
import jstack.eu.messagingApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = "messageForm")
public class MessageFormView extends VerticalLayout implements View {
    private TextArea message;

    private Conversation conversation;
    private Message msg;
    private VerticalLayout messageArea;
    private String currentUsername;
    private String usernameStyle;

    private UserService userService;
    private ConversationService conversationService;
    private EditModal editModal;

    @Autowired
    public MessageFormView(UserService userService, ConversationService conversationService, EditModal editModal) {
        this.userService = userService;
        this.conversationService = conversationService;
        this.editModal = editModal;
        this.message = new TextArea("Type your message here:");

        this.currentUsername = "";
        this.usernameStyle = "leftMessage";
    }

    @PostConstruct
    public void init() {
        final User[] user = new User[1];

        Button homeButton = new Button("Home");
        homeButton.addClickListener(e -> getUI().getNavigator().navigateTo(""));

        Button sendButton = new Button("Send");
        sendButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        sendButton.addClickListener(e -> {
            msg = new Message(message.getValue(), getCookieByName());
            addMessage(msg, true);
        });
        Label label = new Label();
        this.addComponents(homeButton, label, message, sendButton);
        this.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    }

    public void init(VerticalLayout messageArea, Conversation conversation) {
        this.messageArea = messageArea;
        this.conversation = conversation;
        message.focus();
    }

    public void addMessage(Message msg, boolean add) {
        conversation = conversationService.getConversation(conversation.get_id());
        VerticalLayout messageLayout = new VerticalLayout();

        TextArea messages = new TextArea(msg.getUser().getUsername() + "'s message:");
        messages.setStyleName("message");
        messages.setEnabled(false);
        messages.setWordWrap(true);
        messages.setValue(msg.getMessage());
        message.clear();

        if (!currentUsername.equals(msg.getUser().getUsername()) && !currentUsername.isEmpty()) {
            usernameStyle = usernameStyle.equals("leftMessage") ? "rightMessage" : "leftMessage";
        }
        messageLayout.setStyleName(usernameStyle);

        if (add) {
            conversation.addMessage(msg);
            conversationService.addMessage(conversation.get_id(),msg);
        }


        Button editButton = new Button(new ThemeResource("images/edit.png"));
        editButton.addClickListener((Button.ClickListener) clickEvent -> {
            Window editModalWindow = new Window("Edit message");
            editModal.init(messages, msg, conversation, editModalWindow);
            editModalWindow.setContent(editModal);
            editModalWindow.center();
            getUI().addWindow(editModalWindow);
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button deleteButton = new Button(new ThemeResource("images/close-small.png"));
        deleteButton.setStyleName("deleteButton");
        deleteButton.addClickListener((Button.ClickListener) clickEvent -> {
            conversation.removeMessage(msg);
            conversationService.deleteMessage(conversation.get_id(), msg.get_id());
            messageLayout.removeComponent(messages);
            messageLayout.removeComponent(horizontalLayout);
        });
        horizontalLayout.addComponents(editButton, deleteButton);

        User user = ((NavigatorUI) UI.getCurrent()).getUser();
        if (user != null)
            if (user.get_id().equals(msg.getUser().get_id())) {
                messageLayout.addComponents(messages, horizontalLayout);
            } else {
                messageLayout.addComponents(messages);
            }
        messageArea.addComponents(messageLayout);

        currentUsername = msg.getUser().getUsername();
    }

    private User getCookieByName() {
        User user = ((NavigatorUI) UI.getCurrent()).getUser();
        if (user == null) {
            UI.getCurrent().getNavigator().navigateTo("");
        }
        return user;
    }

    public void enter() {
        User user = getCookieByName();
        Label label = (Label) this.getComponent(1);
        if (user != null)
            label.setValue("Logged in as " + user.getUsername());
    }


}

