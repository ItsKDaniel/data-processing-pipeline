package io.falcon.assignment.service.repo;

import io.falcon.assignment.model.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RepositoryOps {
    private final ReactiveRedisOperations<UUID, Payload> redisOps;


    @Autowired
    public RepositoryOps(@Qualifier("repoOps") ReactiveRedisOperations<UUID, Payload> redisOps) {
        this.redisOps = redisOps;
    }
}
