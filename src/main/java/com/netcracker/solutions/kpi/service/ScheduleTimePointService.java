package com.netcracker.solutions.kpi.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author Korzh
 */
public interface ScheduleTimePointService {

    ScheduleTimePoint getScheduleTimePointById(Long id);

    int[] batchInsert(List<Timestamp> timestaps);

    int deleteAll();

    List<ScheduleTimePoint> getFinalTimePointByUserId(Long id);

    Long insertScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int updateScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int deleteScheduleTimePoint(ScheduleTimePoint scheduleTimePoint);

    int deleteUserTimeFinal(User user, ScheduleTimePoint scheduleTimePoint);

	ScheduleTimePoint getScheduleTimePointByTimepoint(Timestamp timestamp);

	List<ScheduleTimePoint> getAll();

	boolean isScheduleDatesExists();
	
	boolean isScheduleExists();

    Map<Long,Long> getUsersNumberInFinalTimePoint(Timestamp timePoint);

    Long addUserToTimepoint(User user, ScheduleTimePoint timePoint);
}
