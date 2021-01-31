package io.falcon.assignment.service.repo;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.service.RepositoryOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static io.falcon.assignment.utils.Constants.PAYLOAD_KEY;

@Slf4j
@Service
public class RepositoryOps implements RepositoryOperations<Payload> {

    private final RedisOperations<String, Payload> redisOps;

    @Autowired
    public RepositoryOps(@Qualifier("repoOps") RedisOperations<String, Payload> redisOps) {
        this.redisOps = redisOps;
    }

    @Override
    public void create(Payload payload) {
        log.debug("Received message {} ", payload.toString());
        redisOps.opsForList().rightPush(PAYLOAD_KEY, payload);
    }

    @Override
    public List<Payload> findAll() {
        List<Payload> payloads = redisOps.opsForList().range(PAYLOAD_KEY, 0, Integer.MAX_VALUE);
        log.debug("Total saved in repo :: {}", payloads != null ? payloads.size() : 0);
        return payloads;
    }

    public void publish(String channel, PayloadRequest message) {
        redisOps.convertAndSend(channel, message);
    }
}
