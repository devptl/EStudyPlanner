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
import com.esp.model.StudentsHasExperts;

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
	 * @param s - {@link Students}
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
	 * @param l1 - {@link LoggedUser}
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
	 * To get the student list with the username
	 * 
	 * @param userName - {@link Students}
	 * @return {@link Students}
	 */
	public Students findStudentByUsername(String userName) {
		return dtoOperation.getStudentsComponents().findOneStudent(userName);
	}

	/**
	 * To find a student with particular username
	 * 
	 * @param userName - {@link Students}
	 * @param email - {@link Students}
	 * @return {@link Students}
	 */
	public boolean findOneStudent(String userName, String email) {

		if (dtoOperation.getStudentsComponents().findOneStudent(userName) == null) {
			return false;
		} else {// checking if student exist
			if (dtoOperation.getStudentsComponents().findOneStudent(userName).getEmail().equals(email)) {

				return true;
			}
			return false;
		}
	}

	/**
	 * When student changes the password
	 * 
	 * @param userName
	 * @param oldPassword
	 * @param newPassword
	 * @return ChangePassword Status
	 */
	public boolean changeTheStudentPassword(String userName, String oldPassword, String newPassword) {

		// checking is student exist
		if (dtoOperation.getStudentsComponents().findOneStudent(userName) == null) {
			return false;
		} else {
			if (dtoOperation.getStudentsComponents().findOneStudent(userName).getPassword().equals(oldPassword)) {
				Students s1 = dtoOperation.getStudentsComponents().findOneStudent(userName);

				// setting the new password
				s1.setPassword(newPassword);
				dtoOperation.getStudentsComponents().saveStudent(s1);
				return true;
			}
			return false;
		}

	}

	/**
	 * To save the student with the courses in join table
	 * 
	 * @param s - {@link StudentsHasExperts}
	 */
	public void saveStudentsAndMinorCourse(StudentsHasCourses s) {
		dtoOperation.getStudentsComponents().saveStudentsHasCourses(s);
	}

	/**
	 * To save the student that has experts in join table
	 * 
	 * @param se- {@link StudentsHasExperts}s
	 */
	public void saveStudentsHasExperts(StudentsHasExperts se) {
		dtoOperation.getStudentsComponents().saveStudentsHasExperts(se);
	}

}
