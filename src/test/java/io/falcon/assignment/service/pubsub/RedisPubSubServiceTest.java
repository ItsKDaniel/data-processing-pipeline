package io.falcon.assignment.service.pubsub;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.service.PayloadConverter;
import io.falcon.assignment.service.repo.RepositoryOps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisPubSubServiceTest {

    @InjectMocks
    private RedisPubSubService service;
    @Mock
    private PayloadConverter converter;
    @Mock
    private RepositoryOps repoOps;

    @Test
    public void listen() {
        PayloadRequest request = new PayloadRequest();
        request.setContent("abrakadabra");
        request.setTimestamp("2018-10-09 00:12:12+0100");

        when(converter.makePayload(any(PayloadRequest.class))).thenReturn(Payload.builder().build());

        service.listen(request);

        verify(converter, times(1)).makePayload(any(PayloadRequest.class));
        verify(repoOps, times(1)).create(any(Payload.class));
    }

    @Test
    public void publish() {
        PayloadRequest request = new PayloadRequest();
        request.setContent("abrakadabra");
        request.setTimestamp("2018-10-09 00:12:12+0100");

        service.publish(request);

        verify(repoOps, times(1)).publish(eq(null), any(PayloadRequest.class));
    }

    @Test
    public void getAllDataFromRepo() {
        service.getAllDataFromRepo();

        verify(repoOps, times(1)).findAll();
    }
}
