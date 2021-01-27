package io.falcon.assignment.service;

public interface EventsService<T> {
    void handle(T message);
}
