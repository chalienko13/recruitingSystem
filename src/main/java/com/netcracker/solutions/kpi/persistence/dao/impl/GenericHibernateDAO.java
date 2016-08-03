package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.GenericDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

@Repository
public abstract class GenericHibernateDAO <T, PK extends Serializable> extends HibernateDaoSupport implements GenericDAO <T, PK>{

    @Autowired
    public void injectSessionFactory(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    private Class<T> type;

    public GenericHibernateDAO(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T newInstance) {
        getHibernateTemplate().persist(newInstance);
    }

    @Override
    public T getByID(PK id) {
        return getHibernateTemplate().get(type, id);
    }

    @Override
    public void update(T transientObject) {
        getHibernateTemplate().update(transientObject);
    }

    @Override
    public void delete(T persistentObject) {
        getHibernateTemplate().delete(persistentObject);
    }

    @Override
    public List<T> getAll() {
        return getHibernateTemplate().loadAll(type);
    }

    @Override
    public Set<T> getAllUnique() {
        List<T> allEntities = getAll();
        if(!CollectionUtils.isEmpty(allEntities)) {
            Set<T> uniqueEntities = new HashSet<>();
            uniqueEntities.addAll(allEntities);

            return uniqueEntities;
        }
        return Collections.EMPTY_SET;
    }

    @Override
    public void deleteAll(Collection<T> entities) {
        getHibernateTemplate().deleteAll(entities);
    }
}
