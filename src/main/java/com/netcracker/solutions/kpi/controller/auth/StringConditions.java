package com.netcracker.solutions.kpi.controller.auth;

import com.google.common.base.Preconditions;

/**
 * Created by IO on 23.04.2016.
 */
public class StringConditions {
    private StringConditions() { }

    public static String checkNotBlank(String string) {
        Preconditions.checkArgument(string != null && string.trim().length() > 0);
        return string;
    }
}
