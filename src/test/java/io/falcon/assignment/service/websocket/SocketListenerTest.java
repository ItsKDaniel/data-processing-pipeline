package io.falcon.assignment.service.websocket;

import io.falcon.assignment.model.PayloadRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SocketListenerTest {

    @InjectMocks
    private SocketListener listener;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Test
    void listen() {
        PayloadRequest request = new PayloadRequest();
        listener.listen(request);

        verify(messagingTemplate, times(1)).convertAndSend(eq(null), eq(request));
    }
}
