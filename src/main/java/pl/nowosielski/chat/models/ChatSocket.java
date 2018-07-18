package pl.nowosielski.chat.models;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import pl.nowosielski.chat.Controllers.LoginController;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class ChatSocket extends TextWebSocketHandler implements WebSocketConfigurer {

    public static List<UserModel> userList = new LinkedList<>();
    public List<String> lastTenMessages = new LinkedList<>();

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(this, "/room").setAllowedOrigins("*");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        UserModel user = LoginController.tmpUser;
        LoginController.tmpUser = null;
        user.setSession(session);
        System.out.println(session.getId());
        userList.add(user);

        System.out.println("Do czatu podłączył się " + user.getLogin());

        lastTenMessages.forEach(s -> {
            try {
                session.sendMessage(new TextMessage(s));
            } catch (IOException e) {
                e.printStackTrace(); }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        removeUserBySession(session);
        System.out.println("Rozłączył się 1 użytkownik");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String login = getLoginBySession(session);
        String personalMessage;

        if (message.getPayload().length() > 150) {
            String lenghtWarningMessage = "Twoja wiadomość jest zbyt długa";
            session.sendMessage(new TextMessage(lenghtWarningMessage));
        } else {
            personalMessage = login + ": " + message.getPayload();
            addMessageToLastTenList(personalMessage);

            userList.forEach(s -> {
                try {
                    s.getSession().sendMessage(new TextMessage(personalMessage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }); }
    }

    public void addMessageToLastTenList(String message) {
        if (lastTenMessages.size() >= 10)
            lastTenMessages.remove(0);
        lastTenMessages.add(message);
    }

    public static String getLoginBySession(WebSocketSession session) {
        String login = null;
        for (int i = 0; i < userList.size(); i++) {
            UserModel user = userList.get(i);
            if (user.getSession().equals(session)) {
                login = user.getLogin(); } }
        return login;
    }

    public void removeUserBySession(WebSocketSession session) {
        for (int i = 0; i < userList.size(); i++) {
            UserModel user = userList.get(i);
            if (user.getSession().equals(session)) {
                userList.remove(user); } } }
}
