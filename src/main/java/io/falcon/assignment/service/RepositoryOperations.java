package io.falcon.assignment.service;

import java.util.List;

public interface RepositoryOperations<DATA> {
    void create(DATA payload);

    List<DATA> findAll();
}
