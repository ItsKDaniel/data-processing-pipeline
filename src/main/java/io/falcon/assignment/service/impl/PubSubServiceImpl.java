package io.falcon.assignment.service.impl;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.falcon.assignment.utils.Constants.PUB_SUB_CHANNEL;

@Service
public class PubSubServiceImpl implements EventsService<Payload> {

    private final RedisTemplate<UUID, Payload> dbTemplate;

    @Autowired
    public PubSubServiceImpl(RedisTemplate<UUID, Payload> dbTemplate) {
        this.dbTemplate = dbTemplate;
    }

    @Override
    public void handle(Payload message) {
        dbTemplate.convertAndSend(PUB_SUB_CHANNEL, message);
    }
}
