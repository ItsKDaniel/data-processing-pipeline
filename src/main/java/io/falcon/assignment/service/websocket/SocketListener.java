package io.falcon.assignment.service.websocket;

import io.falcon.assignment.model.PayloadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketListener {

    public final SimpMessagingTemplate messagingTemplate;
    @Value("${socket.topic:/topic/publish}")
    private String topic;

    @Autowired
    public SocketListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void listen(PayloadRequest message) {
        messagingTemplate.convertAndSend(topic, message);
    }

}
