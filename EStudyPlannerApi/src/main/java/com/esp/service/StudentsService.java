package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.esp.dto.DtoOperation;
import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.Schedule;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;

@Service
public class StudentsService {

	@Autowired
	private DtoOperation dtoOperation;

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private Initialiser initialiser;

	/**
	 * When a particular student registers and data is to saved in the database
	 * 
	 * @param s
	 * @param model
	 * @return registrationStatus
	 */
	public boolean studentRegistration(Students s, ModelMap model) {
		String id = s.getUserName();
		if (dtoOperation.getStudentsComponents().findOneStudent(id) == null) {
			s.setGuardiansIdGuardians(1);
			String username = s.getUserName();

			// getting the main course list according to registered field
			ArrayList<Courses> mainCourses = coursesService.mainCoursesById(s.getField());

			// default list of experts
			ArrayList<Experts> allExperts = dtoOperation.getExpertsComponents().allExperts();

			// assign a new schedule
			Schedule schedule = new Schedule();

			// Set the messege
			String msg = "Enter the schedule";

			// initialising values for schedule page
			initialiser.schedulerInitialiser(mainCourses, allExperts, schedule, username, model, msg);

			dtoOperation.getStudentsComponents().saveStudent(s);

			return true;
		} else
			return false;
	}

	/**
	 * When a student logs in and the Scheduler has to be set
	 * 
	 * @param l1
	 * @param model
	 * @return loginStatus
	 */
	public boolean studentsLogin(LoggedUser l1, ModelMap model) {

		String loginId = l1.getUserName();
		if (dtoOperation.getStudentsComponents().findOneStudent(loginId) == null) {
			return false;
		} else {
			if (dtoOperation.getStudentsComponents().findOneStudent(loginId).getPassword().equals(l1.getPassword())) {
				Students s1 = dtoOperation.getStudentsComponents().findOneStudent(loginId);
				String username = s1.getUserName();
				Schedule schedule;

				// set the main course according to the fiels
				ArrayList<Courses> mainCourses = coursesService.mainCoursesById(s1.getField());

				// check if schedule already exist
				if (scheduleService.findSchedule(username) == null) {
					schedule = new Schedule();
				} else {
					schedule = scheduleService.findSchedule(username);
				}

				// setting the expert list
				ArrayList<Experts> allExperts = dtoOperation.getExpertsComponents().allExperts();
				String msg = "Want to make changes in schedule";

				// initialising values for schedule page
				initialiser.schedulerInitialiser(mainCourses, allExperts, schedule, username, model, msg);

				return true;
			}

			return false;
		}

	}
	
	
	/**
	 * To save the user with the courses in join table
	 * 
	 * @param s
	 */
	public void saveStudentsAndMinorCourse(StudentsHasCourses s) {
		dtoOperation.getStudentsComponents().saveStudentsHasCourses(s);
	}

}
