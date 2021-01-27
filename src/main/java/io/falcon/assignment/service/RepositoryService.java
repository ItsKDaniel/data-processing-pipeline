package io.falcon.assignment.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class RepositoryService<Key, Value> {
    @Getter
    @Setter
    RedisTemplate<Key, Value> redisTemplate;

    public abstract int create(Value payload);

    protected abstract Value findById(Key id);


}
