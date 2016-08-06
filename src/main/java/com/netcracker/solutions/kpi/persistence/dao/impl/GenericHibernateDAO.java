package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.GenericDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

//import org.hibernate.query.Query;

@Repository
public abstract class GenericHibernateDAO<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDAO<T, PK> {

    private Class<T> type;

    public GenericHibernateDAO(Class<T> type) {
        this.type = type;
    }

    @Autowired
    public void injectSessionFactory(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
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
        if (!CollectionUtils.isEmpty(allEntities)) {
            Set<T> uniqueEntities = new HashSet<>();
            uniqueEntities.addAll(allEntities);

            return uniqueEntities;
        }
        return Collections.EMPTY_SET;
    }

//    public List<T> queryForList(String queryString, Objects... params){
//         Query query = getSessionFactory().getCurrentSession().createQuery(queryString);
//        int rowNum = 1;
//         for (Object param : params){
//             query.setParameter(rowNum++, param);
//         }
//        return query.getResultList();
//    }

    @Override
    public void deleteAll(Collection<T> entities) {
        getHibernateTemplate().deleteAll(entities);
    }
}
