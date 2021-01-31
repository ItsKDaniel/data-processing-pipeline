package io.falcon.assignment.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.falcon.assignment.common.model.Payload;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.UUID;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host:localhost}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.password:}")
    private String password;
    @Value("${spring.redis.database:0}")
    private int database;

    @Primary
    @Bean(name = "connectionFactory")
    public ReactiveRedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(RedisPassword.of(password));
        config.setDatabase(database);

        return new LettuceConnectionFactory(config);
    }

    @Bean("repoOps")
    public ReactiveRedisOperations<UUID, Payload> redisTemplate(@Qualifier("connectionFactory") ReactiveRedisConnectionFactory lettuceConnectionFactory,
                                                                @Qualifier("redisSerializer") RedisSerializer<Payload> redisSerializer) {
        RedisSerializationContext<UUID, Payload> serializationContext = RedisSerializationContext
                .<UUID, Payload>newSerializationContext(RedisSerializer.string())
                .value(redisSerializer)
                .build();
        return new ReactiveRedisTemplate<UUID, Payload>(lettuceConnectionFactory, serializationContext);
    }

    @Bean(name = "redisSerializer")
    public Jackson2JsonRedisSerializer<Payload> redisSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Jackson2JsonRedisSerializer<Payload> serializer = new Jackson2JsonRedisSerializer<>(Payload.class);
        serializer.setObjectMapper(mapper);

        return serializer;
    }

}
