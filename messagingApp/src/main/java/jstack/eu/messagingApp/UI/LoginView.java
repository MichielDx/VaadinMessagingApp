package jstack.eu.messagingApp.UI;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import jstack.eu.messagingApp.models.User;
import jstack.eu.messagingApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;

@UIScope
@SpringView(name = "loginView")
public class LoginView extends VerticalLayout implements View {
    private RegisterModal registerModal;

    private UserService userService;


    @Autowired
    public LoginView(RegisterModal registerModal, UserService userService) {
        this.registerModal = registerModal;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        TextField usernameField = new TextField("Username:");
        usernameField.focus();
        PasswordField passwordField = new PasswordField("Password:");

        Button loginButton = new Button("Login");
        loginButton.setStyleName("loginFormButton");
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        Button registerButton = new Button("Register");
        registerButton.setStyleName("loginFormButton");

        loginButton.addClickListener((Button.ClickListener) clickEvent -> {
            User user = userService.getUserByName(usernameField.getValue());
            if(userService.login(usernameField.getValue(),passwordField.getValue())){
                Cookie cookie = new Cookie("userId", user.get_id());
                cookie.setMaxAge(3600);
                cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
                VaadinService.getCurrentResponse().addCookie(cookie);

                ((NavigatorUI) UI.getCurrent()).setUser(user);

                getUI().getNavigator().navigateTo("home");
            } else {
                getUI().addWindow(registerModalWindow(usernameField, passwordField));
            }
        });

        registerButton.addClickListener((Button.ClickListener) clickEvent -> getUI().addWindow(registerModalWindow(usernameField, passwordField)));

        HorizontalLayout horizontalLayout = new HorizontalLayout(new VerticalLayout(usernameField, passwordField, new HorizontalLayout(loginButton, registerButton)));
        horizontalLayout.setStyleName("loginForm");
        this.addComponent(horizontalLayout);
        this.setSizeFull();
        this.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
    }

    private Window registerModalWindow(TextField usernameField, PasswordField passwordField) {
        Window registerModalWindow = new Window("Edit message");
        registerModal.init(usernameField.getValue(), passwordField.getValue(), registerModalWindow);
        registerModalWindow.setContent(registerModal);
        registerModalWindow.center();
        return registerModalWindow;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(((NavigatorUI) UI.getCurrent()).getUser()!=null){
            getUI().getNavigator().navigateTo("home");
        }
    }
}
