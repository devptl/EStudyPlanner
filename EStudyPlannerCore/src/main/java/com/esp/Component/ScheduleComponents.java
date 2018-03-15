package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.model.Schedule;
import com.esp.repository.ScheduleRepository;

@Service
public class ScheduleComponents {
	
	@Autowired
	private ScheduleRepository scheduleRepository; 
	

	/**
	 * To get a particular schedule
	 * @param id
	 * @return {@link Schedule}
	 */
	public Schedule findOneSchedule(String id) {
		return scheduleRepository.findOne(id); 
	}
	
	/**
	 * To get the all the schedules 
	 * @return {@link Schedule}
	 */
	public ArrayList<Schedule> allSchedule()
	{
		return (ArrayList<Schedule>) scheduleRepository.findAll();
	}
	
	/**
	 * To save a schedule in database
	 * @param s
	 */
	public void saveSchedule(Schedule s) {
		scheduleRepository.save(s);
	}

}
