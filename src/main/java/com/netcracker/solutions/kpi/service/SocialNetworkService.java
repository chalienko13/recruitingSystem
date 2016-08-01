package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;

/**
 * Created by IO on 16.04.2016.
 */
public interface SocialNetworkService {

    SocialNetwork getByID(Long id);

}
