package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;

/**
 * Created by IO on 16.04.2016.
 */
public interface SocialNetworkDao {

    SocialNetwork getByID(Long id);

}
