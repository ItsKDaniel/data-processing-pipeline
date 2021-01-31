package io.falcon.assignment.service;

/**
 * Events listener that publishes to or listens from sockets or pubsub stream
 *
 * @param <T> Payload
 */
public interface Events<T> {

    void listen(T message);

    void publish(T message);
}
