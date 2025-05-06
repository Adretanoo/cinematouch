package com.adrian.infrastructure.persistence.dao;

import java.util.List;
import java.util.Optional;

/**
 * Універсальний контракт для CRUD-операцій над сутністю T з ідентифікатором ID.
 */
public interface GenericDao<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
