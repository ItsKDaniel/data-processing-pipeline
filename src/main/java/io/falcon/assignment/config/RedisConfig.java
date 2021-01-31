package io.falcon.assignment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
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
    public RedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(RedisPassword.of(password));
        config.setDatabase(database);

        return new LettuceConnectionFactory(config);
    }

    @Bean("repoOps")
    public RedisOperations<String, Payload> redisTemplate(@Qualifier("connectionFactory") RedisConnectionFactory lettuceConnectionFactory,
                                                        @Qualifier("redisSerializer") RedisSerializer<Payload> redisSerializer) {
        RedisTemplate<String, Payload> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setValueSerializer(redisSerializer);

        return redisTemplate;
    }

    @Bean(name = "redisSerializer")
    public Jackson2JsonRedisSerializer<Payload> redisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Payload.class);
    }

    @Bean(name = "publisherSerializer")
    public Jackson2JsonRedisSerializer<PayloadRequest> publisherSerializer() {
        return new Jackson2JsonRedisSerializer<>(PayloadRequest.class);
    }

}
