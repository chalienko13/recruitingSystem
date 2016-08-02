package com.netcracker.solutions.kpi.persistence.util;

import java.util.Comparator;


/**
 * Created by Admin on 14.05.2016.
 */
public class FormQuestionComparator implements Comparator<FormQuestion> {

    @Override
    public int compare(FormQuestion o1, FormQuestion o2) {
        if(o1.getOrder() < o2.getOrder()){
            return -1;
        } else {
            return 1;
        }
    }

}
