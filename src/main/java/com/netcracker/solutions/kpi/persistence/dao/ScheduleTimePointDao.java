package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author Korzh
 */
public interface ScheduleTimePointDao {

    ScheduleTimePoint getFinalTimePointById(Long id);

    int deleteAll();

    int[] batchInsert(List<Timestamp> timestaps);

    List<ScheduleTimePoint> getFinalTimePointByUserId(Long id);

    Long insertScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int updateScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int deleteScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int deleteUserTimeFinal(User user, ScheduleTimePoint scheduleTimePoint);

	ScheduleTimePoint getScheduleTimePointByTimepoint(Timestamp timestamp);

	List<ScheduleTimePoint> getAll();

	boolean isScheduleExists();

	boolean isScheduleDatesExists();

    Map<Long,Long> getUsersNumberInFinalTimePoint(Timestamp timePoint);

    Long addUserToTimepoint(User user, ScheduleTimePoint timePoint);
}
