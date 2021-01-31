package io.falcon.assignment.api.service.repo;

import io.falcon.assignment.common.model.Payload;
import io.falcon.assignment.common.service.RepositoryOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RepositoryOps implements RepositoryOperations<Payload> {
    private final ReactiveRedisOperations<UUID, Payload> redisOps;


    @Autowired
    public RepositoryOps(@Qualifier("repoOps") ReactiveRedisOperations<UUID, Payload> redisOps) {
        this.redisOps = redisOps;
    }

    @Override
    public void create(Payload payload) {

    }
}
