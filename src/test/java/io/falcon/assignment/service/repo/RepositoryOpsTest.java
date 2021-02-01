package io.falcon.assignment.service.repo;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;

import static io.falcon.assignment.utils.Constants.PAYLOAD_KEY;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryOpsTest {

    @InjectMocks
    private RepositoryOps ops;

    @Mock
    private RedisOperations<String, Payload> redisOps;


    @Test
    void create() {
        when(redisOps.opsForList()).thenReturn(mock(ListOperations.class));

        Payload payload = Payload.builder().build();
        ops.create(payload);

        verify(redisOps, times(1)).opsForList();
        verify(redisOps.opsForList(), times(1)).rightPush(eq(PAYLOAD_KEY), eq(payload));
    }

    @Test
    void findAll() {
        when(redisOps.opsForList()).thenReturn(mock(ListOperations.class));

        ops.findAll();

        verify(redisOps, times(1)).opsForList();
        verify(redisOps.opsForList(), times(1))
                .range(eq(PAYLOAD_KEY), eq(0L), eq(Long.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    void publish() {
        PayloadRequest request = new PayloadRequest();
        ops.publish("", request);

        verify(redisOps, times(1)).convertAndSend(eq(""), eq(request));
    }
}
