package io.falcon.assignment.websocket.service;

import io.falcon.assignment.common.model.Payload;
import io.falcon.assignment.common.service.Events;

public class SocketListener implements Events<Payload> {
    @Override
    public void listen(Payload message) {

    }

    @Override
    public void publish(Payload message) {

    }
}
