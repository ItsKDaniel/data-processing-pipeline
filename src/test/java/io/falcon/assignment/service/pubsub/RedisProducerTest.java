package io.falcon.assignment.service.pubsub;

import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.service.repo.RepositoryOps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RedisProducerTest {

    @InjectMocks
    private RedisProducer service;
    @Mock
    private RepositoryOps repoOps;

    @Test
    void publish() {
        PayloadRequest request = new PayloadRequest();
        request.setContent("abrakadabra");
        request.setTimestamp("2018-10-09 00:12:12+0100");

        service.publish(request);

        verify(repoOps, times(1)).publish(eq(null), any(PayloadRequest.class));
    }
}
