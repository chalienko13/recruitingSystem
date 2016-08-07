package com.netcracker.solutions.kpi.persistence.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GenericDAO<T, PK extends Serializable> {
    T getByID(PK id);

    void update(T transientObject);

    void delete(T persistentObject);

    List<T> getAll();

    Set<T> getAllUnique();

    void deleteAll(Collection<T> entities);
}
