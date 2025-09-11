package com.example.book_service.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import org.springframework.ai.chat.client.ChatClient;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ChatClient chatClient;

    public ChatWebSocketHandler(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {

        String botResponse = chatClient.prompt().user(message.getPayload()).call().content();

        session.sendMessage(new TextMessage(botResponse));
    }
}
