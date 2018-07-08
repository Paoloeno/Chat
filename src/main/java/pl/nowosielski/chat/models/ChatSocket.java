package pl.nowosielski.chat.models;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatSocket extends TextWebSocketHandler implements WebSocketConfigurer{

    public List <WebSocketSession> userList = new ArrayList<>();

    //Dodajemy handlera do websocketa. W związku z tym, że klasa ChatSocket jest websocketHandlerem (rozszerza TextWebSocketHandler) możemy wpisać po prostu słówko kluczowe "this". Drugi parametr to adres pod jakim się można połączyć z socketem. Metoda setAllowedOrigins określa z jakich adresów ip możemy się podłączyć do czatu - tutaj gwiazdka oznacza wszystkie.
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(this, "/room").setAllowedOrigins("*");
    }

    //Co się ma stać jak ktoś nawiąże połączenie z naszym socketem
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        userList.add(session);
        System.out.println("Podłączył się 1 użytkownik");
    }

    //Co się ma stać jak ktoś wyjdzie
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userList.remove(session);
        System.out.println("Rozłączył się 1 użytkownik");
    }

    //Co ma się stać, gdy ktoś wyśle wiadomość
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        //Gdy ktoś wyśle wiadomość, to iterujemy po wszystkich użytkownikach i do wszystkich ją przesyłamy
        userList.forEach(s -> {
            try {
                s.sendMessage(new TextMessage(message.getPayload()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
