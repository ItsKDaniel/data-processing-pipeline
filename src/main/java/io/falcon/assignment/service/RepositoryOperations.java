package io.falcon.assignment.service;

import java.util.List;

public interface RepositoryOperations<DATA> {
    /**
     * Inserts the data into the repository
     *
     * @param payload to be inserted
     */
    void create(DATA payload);

    /**
     * Retrieve all data from the repository
     *
     * @return list of records
     */
    List<DATA> findAll();
}
