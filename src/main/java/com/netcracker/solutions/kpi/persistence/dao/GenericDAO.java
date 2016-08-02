package com.netcracker.solutions.kpi.persistence.dao;

import java.io.Serializable;

public interface GenericDAO <T, PK extends Serializable> {

    void create(T newInstance);

    T getByID(PK id);

    void update(T transientObject);

    void delete(T persistentObject);
}
