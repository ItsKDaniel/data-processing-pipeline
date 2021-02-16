package io.falcon.assignment.service.pubsub;

import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.service.repo.RepositoryOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RedisProducer {
    private final RepositoryOps repoOps;

    @Value("${redis.topic.name:_messages_}")
    private String topic;

    @Autowired
    public RedisProducer(RepositoryOps repoOps) {
        this.repoOps = repoOps;
    }

    /**
     * Publishes message to Redis PubSub topic
     *
     * @param message Payload
     */
    public void publish(PayloadRequest message) {
        repoOps.publish(topic, message);
    }
}
