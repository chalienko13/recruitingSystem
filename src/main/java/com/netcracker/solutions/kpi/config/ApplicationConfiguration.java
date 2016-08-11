package com.netcracker.solutions.kpi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApplicationConfiguration {
    @Value("${server.url}")
    public String serverUrl;

    @Value("${token.expire.time}")
    public Long tokenExpireTime;

    @Resource
    public Environment environment;
}
