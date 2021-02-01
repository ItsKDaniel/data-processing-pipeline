package io.falcon.assignment.service;

import java.util.List;

public interface RepositoryOperations<T> {
    /**
     * Inserts the data into the repository
     *
     * @param payload to be inserted
     */
    void create(T payload);

    /**
     * Retrieve all data from the repository
     *
     * @return list of records
     */
    List<T> findAll();
}
