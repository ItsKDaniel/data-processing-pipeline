package io.falcon.assignment.service.pubsub;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.service.helper.PayloadConverter;
import io.falcon.assignment.service.repo.RepositoryOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisConsumer {
    private final PayloadConverter converter;
    private final RepositoryOps repoOps;

    @Autowired
    public RedisConsumer(PayloadConverter converter, RepositoryOps repoOps) {
        this.converter = converter;
        this.repoOps = repoOps;
    }

    /**
     * Listen for the message from Redis PubSub topic and persists the data into Redis
     *
     * @param message payload subscribed from PubSub topic
     */
    public void listen(PayloadRequest message) {
        Payload payload = converter.makePayload(message);
        repoOps.create(payload);
    }

    /**
     * Retrieve all data from redis
     *
     * @return all data persisted
     */
    public List<Payload> getAllDataFromRepo() {
        return repoOps.findAll();
    }

}
