package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.GenericDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.io.Serializable;

public class GenericHibernateDAO <T, PK extends Serializable> extends HibernateDaoSupport implements GenericDAO <T, PK>{

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
}
