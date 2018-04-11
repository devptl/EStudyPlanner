package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esp.model.Schedule;
import com.esp.repository.ScheduleRepository;

/**
 * Component class to provide components on schedule
 * findOneSchedule()                             -  To get a particular schedule
 * allSchedule()                                 -  To get the all the schedules
 * saveSchedule()                                -  To save a schedule in database
 * 
 * @author mindfire
 *
 */
@Component
public class ScheduleComponents {

	@Autowired
	private ScheduleRepository scheduleRepository;

	/**
	 * To get a particular schedule
	 * 
	 * @param id
	 *            - ScheduleID
	 * @return {@link Schedule}
	 */
	public Schedule findOneSchedule(String id) {
		return scheduleRepository.findOne(id);
	}

	/**
	 * To get the all the schedules
	 * 
	 * @return {@link Schedule}
	 */
	public ArrayList<Schedule> allSchedule() {
		return (ArrayList<Schedule>) scheduleRepository.findAll();
	}

	/**
	 * To save a schedule in database
	 * 
	 * @param s
	 *            - {@link Schedule}
	 */
	public void saveSchedule(Schedule s) {
		scheduleRepository.save(s);
	}

}
