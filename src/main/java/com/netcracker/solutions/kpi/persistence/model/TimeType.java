package com.netcracker.solutions.kpi.persistence.model;

import javax.persistence.*;

/**
 * Created by olil0716 on 8/4/2016.
 */
@Entity
@Table(name = "time_type")
public class TimeType {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Short id;

}
