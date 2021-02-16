package io.falcon.assignment.config;

import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.service.pubsub.RedisConsumer;
import io.falcon.assignment.service.websocket.SocketListener;
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

    // listens to the redis pubsub topic and handles the received message
    @Bean(name = "pubSubListener")
    public MessageListenerAdapter pubSubListener(RedisConsumer listener,
                                                 @Qualifier("publisherSerializer") RedisSerializer<PayloadRequest> redisSerializer) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener, "listen");
        adapter.setSerializer(redisSerializer);
        return adapter;
    }

    // broadcasts the data received by the pubsub topic to a websocket
    @Bean(name = "broadcastListener")
    public MessageListenerAdapter broadcastListener(SocketListener listener,
                                                    @Qualifier("publisherSerializer") RedisSerializer<PayloadRequest> redisSerializer) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener, "listen");
        adapter.setSerializer(redisSerializer);
        return adapter;
    }

    @Bean
    public RedisMessageListenerContainer messageListener(@Qualifier("connectionFactory") RedisConnectionFactory connectionFactory,
                                                         @Qualifier("pubSubListener") MessageListener pubSubListener,
                                                         @Qualifier("broadcastListener") MessageListener broadcastListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // listens to incoming data on the topic and handles by triggering the registered handler
        container.addMessageListener(pubSubListener, ChannelTopic.of(topic));
        container.addMessageListener(broadcastListener, ChannelTopic.of(topic));

        return container;
    }
}
