package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.esp.Component.ExpertsComponents;
import com.esp.Component.ScheduleComponents;
import com.esp.Component.StudentsComponents;
import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.Schedule;
import com.esp.model.Students;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleComponents scheduleComponents;
	
	@Autowired
	private ExpertsComponents expertsComponents;
	
	@Autowired
	private StudentsComponents studentsComponents;

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private Initialiser initialiser;

	/**
	 * To get the difference of timing
	 * 
	 * @param schedule
	 * @return {@link Integer}
	 */
	public int[] checkSchedule(Schedule schedule) {

		// difference of the timmings
		int[] diff = new int[3];

		diff[0] = differnceOfTime(schedule.getSlotOneIn(), schedule.getSlotOneOut());
		diff[1] = differnceOfTime(schedule.getSlotTwoIn(), schedule.getSlotTwoOut());
		diff[2] = differnceOfTime(schedule.getSlotThreeIn(), schedule.getSlotThreeOut());

		return diff;
	}

	/**
	 * Get the diffence between time in and out
	 * 
	 * @param t1 - time in hours
	 * @param t2 - time in mins
	 * @return {@link Integer}
	 */
	public static int differnceOfTime(String t1, String t2) {

		// getting the hours and minutes to calculate difference
		int intt1h = Integer.parseInt(t1.substring(0, 2));
		int intt1m = Integer.parseInt(t1.substring(3, 5));

		int intt2h = Integer.parseInt(t2.substring(0, 2));
		int intt2m = Integer.parseInt(t2.substring(3, 5));

		// getting the difference in minutes
		int diff = ((intt2h - intt1h) * 60) + (intt2m - intt1m);
		return diff;

	}

	/**
	 * When dont want to save schedule due to wrong input
	 * 
	 * @param schedule
	 * @param model
	 */
	public void dontSaveSchedule(Schedule schedule, ModelMap model) {
		String msg = "Time difference is not correct";
		manageSchedule(schedule, model, msg);
	}

	/**
	 * To save the schedule in the database and set the other display
	 * 
	 * @param s - {@link Schedule}
	 * @param model
	 */
	public void saveSchedule(Schedule schedule, ModelMap model) {

		// saving the schedule
		scheduleComponents.saveSchedule(schedule);
		String msg = "Time difference is correct";
		manageSchedule(schedule, model, msg);

	}

	/**
	 * To set the content for the schedule page
	 * 
	 * @param schedule
	 * @param model
	 * @param message
	 */
	public void manageSchedule(Schedule schedule, ModelMap model, String message) {

		Students student = studentsComponents.findOneStudent(schedule.getStudentsUserName());

		// getting the list of courses according to the fields
		ArrayList<Courses> mainCourses = coursesService.mainCoursesById(student.getField());

		ArrayList<Experts> allExperts = expertsComponents.allExperts();

		initialiser.schedulerInitialiser(mainCourses, allExperts, schedule, schedule.getStudentsUserName(), model,
				message);

	}

	/**
	 * To find the schedule with the particualar username
	 * 
	 * @param username - {@link Students}
	 * @return {@link Schedule}
	 */
	public Schedule findSchedule(String username) {
		return scheduleComponents.findOneSchedule(username);
	}

}
