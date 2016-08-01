package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.SocialNetworkDao;
import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.service.SocialNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialNetworkServiceImpl implements SocialNetworkService {

    @Autowired
    private SocialNetworkDao socialNetworkDao;

    /*public SocialNetworkServiceImpl(SocialNetworkDao socialNetworkDao) {
        this.socialNetworkDao = socialNetworkDao;
    }*/

    @Override
    public SocialNetwork getByID(Long id) {
        return socialNetworkDao.getByID(id);
    }
}
