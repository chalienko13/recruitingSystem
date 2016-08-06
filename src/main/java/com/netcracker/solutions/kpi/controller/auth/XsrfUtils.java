package com.netcracker.solutions.kpi.controller.auth;

import java.math.BigInteger;
import java.security.SecureRandom;

public class XsrfUtils {
    public static final String XSRF_KEY = "xsrf-token";

    private static XsrfUtils xsrfUtils;

    private XsrfUtils() {

    }

    public static XsrfUtils getInstance() {
        if (xsrfUtils == null) {
            xsrfUtils = new XsrfUtils();
        }
        return xsrfUtils;
    }

    public String newToken() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    public boolean isValid(String expectedToken, String actualToken) {
        return expectedToken != null && expectedToken.equals(actualToken);
    }
}
