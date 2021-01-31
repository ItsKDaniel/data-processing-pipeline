package io.falcon.assignment.service.pubsub;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.service.Events;
import io.falcon.assignment.service.PayloadConverter;
import io.falcon.assignment.service.repo.RepositoryOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Redis PubSub related ops go here
 */
@Service
public class RedisPubSubService implements Events<PayloadRequest> {

    private final PayloadConverter converter;
    private final RepositoryOps repoOps;

    @Value("${redis.topic.name:_messages_}")
    private String topic;

    @Autowired
    public RedisPubSubService(PayloadConverter converter, RepositoryOps repoOps) {
        this.converter = converter;
        this.repoOps = repoOps;
    }

    /**
     * Listen for the message from Redis PubSub topic and persists the data into Redis
     *
     * @param message payload subscribed from PubSub topic
     */
    @Override
    public void listen(PayloadRequest message) {
        Payload payload = converter.makePayload(message);
        repoOps.create(payload);
    }

    /**
     * Publishes message to Redis PubSub topic
     *
     * @param message Payload
     */
    @Override
    public void publish(PayloadRequest message) {
        repoOps.getRedisOps()
                .convertAndSend(topic, message);
    }

    public List<Payload> getAllDataFromRepo() {
        return repoOps.findAll();
    }
}
