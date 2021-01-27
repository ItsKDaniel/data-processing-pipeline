package io.falcon.assignment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.falcon.assignment.model.Payload;
import io.falcon.assignment.service.impl.PubSubServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.UUID;

@Configuration
public class Factory {

    @Value("${spring.redis.host:localhost}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.password:}")
    private String password;
    @Value("${spring.redis.database:0}")
    private int database;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(RedisPassword.of(password));
        config.setDatabase(database);

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(""));

        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(PubSubServiceImpl receiver,
                                                  Jackson2JsonRedisSerializer<Payload> redisSerializer) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver);
        adapter.setSerializer(redisSerializer);
        return adapter;
    }

    @Bean
    public RedisTemplate<UUID, Payload> redisTemplate(RedisConnectionFactory connectionFactory,
                                                      Jackson2JsonRedisSerializer<Payload> redisSerializer) {
        RedisTemplate<UUID, Payload> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(redisSerializer);
        return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Payload> redisSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Jackson2JsonRedisSerializer<Payload> serializer = new Jackson2JsonRedisSerializer<>(Payload.class);
        serializer.setObjectMapper(mapper);

        return serializer;
    }

}
