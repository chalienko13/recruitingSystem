package com.netcracker.solutions.kpi.controller.auth;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by IO on 23.04.2016.
 */
public class XsrfUtils {
    public static final String XSRF_KEY = "xsrf-token";

    private static XsrfUtils xsrfUtils;

    public static XsrfUtils getInstance(){
        if (xsrfUtils == null){
            xsrfUtils = new XsrfUtils();
        }
        return xsrfUtils;
    }

    private XsrfUtils(){

    }

    public String newToken() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    public boolean isValid(String expectedToken, String actualToken) {
        return expectedToken != null && expectedToken.equals(actualToken);
    }
}
