package io.falcon.assignment.api.service.pubsub;

import io.falcon.assignment.common.model.Payload;
import io.falcon.assignment.common.service.Events;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Redis PubSub related ops go here
 */
@Service
public class RedisPubSubService implements Events<Payload> {

    @Value("${redis.topic.name:_messages_}")
    private String topic;

    /**
     * Listen for the message from Redis PubSub topic
     *
     * @param message payload from PubSub
     */
    @Override
    public void listen(Payload message) {

    }

    /**
     * Publishes message to Redis PubSub
     *
     * @param message Payload
     */
    @Override
    public void publish(Payload message) {

    }
}
