package pl.nowosielski.chat.models;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class UserModel {

    private String login;
    private WebSocketSession session;

    public UserModel(String login){
        this.login = login;
    }
}
