package io.falcon.assignment.common.service;

import io.falcon.assignment.common.model.Payload;

public interface RepositoryOperations<DATA> {
    void create(DATA payload);
}
