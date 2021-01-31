package io.falcon.assignment.config;

import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.service.pubsub.RedisPubSubService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class DataListenerConfig {

    @Value("${redis.topic.name:_messages_}")
    private String topic;

    @Bean(name = "pubSubListener")
    public MessageListenerAdapter pubSubListener(RedisPubSubService listener,
                                                 @Qualifier("publisherSerializer") RedisSerializer<PayloadRequest> redisSerializer) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener, "listen");
        adapter.setSerializer(redisSerializer);
        return adapter;
    }

    @Bean
    public RedisMessageListenerContainer messageListener(@Qualifier("connectionFactory") RedisConnectionFactory connectionFactory,
                                                         @Qualifier("pubSubListener") MessageListener pubSubListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(pubSubListener, ChannelTopic.of(topic));

        return container;
    }
}
