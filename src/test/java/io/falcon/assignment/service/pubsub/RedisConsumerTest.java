package io.falcon.assignment.service.pubsub;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.service.helper.PayloadConverter;
import io.falcon.assignment.service.repo.RepositoryOps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisConsumerTest {
    @InjectMocks
    private RedisConsumer consumer;
    @Mock
    private PayloadConverter converter;
    @Mock
    private RepositoryOps repoOps;

    @Test
    void listen() {
        PayloadRequest request = new PayloadRequest();

        when(converter.makePayload(any(PayloadRequest.class))).thenReturn(Payload.builder().build());

        consumer.listen(request);

        verify(converter, times(1)).makePayload(any(PayloadRequest.class));
        verify(repoOps, times(1)).create(any(Payload.class));
    }

    @Test
    void getAllDataFromRepo() {
        consumer.getAllDataFromRepo();

        verify(repoOps, times(1)).findAll();
    }
}
