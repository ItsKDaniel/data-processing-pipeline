package io.falcon.assignment.service.impl;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.service.RepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class RepositoryServiceImpl extends RepositoryService<UUID, Payload> {

    @Autowired
    public RepositoryServiceImpl(RedisTemplate<UUID, Payload> dbTemplate) {
        super.setRedisTemplate(dbTemplate);
    }

    @Override
    public int create(Payload payload) {
        try {
            getRedisTemplate()
                    .opsForValue()
                    .set(UUID.randomUUID(), payload);
        } catch (Exception ex) {
            log.error("Failed in persisting to DB", ex);
            return 0;
        }
        return 1;
    }

    public Payload findById(String id) {
        return findById(UUID.fromString(id));
    }

    @Override
    protected Payload findById(UUID id) {
        Optional<Payload> result = Optional.ofNullable(
                getRedisTemplate()
                        .opsForValue()
                        .get(id)
        );

        return result.orElseThrow();
    }
}
