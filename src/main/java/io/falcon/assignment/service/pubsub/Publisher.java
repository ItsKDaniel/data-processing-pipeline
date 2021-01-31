package io.falcon.assignment.service.pubsub;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.service.Events;
import org.springframework.stereotype.Service;

@Service
public class Publisher implements Events<Payload> {

    @Override
    public void listen(Payload message) {

    }

    @Override
    public void publish(Payload message) {

    }
}
