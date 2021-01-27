package io.falcon.assignment.service.impl;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.service.EventsService;
import org.springframework.stereotype.Service;

@Service
public class WebSocketListenerService implements EventsService<Payload> {
    @Override
    public void handle(Payload message) {

    }
}
