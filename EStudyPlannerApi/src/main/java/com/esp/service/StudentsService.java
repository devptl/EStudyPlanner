package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.esp.dto.DtoOperation;
import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.RegisteredUser;
import com.esp.model.Schedule;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.model.StudentsHasExperts;
import com.esp.model.Users;

@Service
public class StudentsService {

	@Autowired
	private DtoOperation dtoOperation;

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private Encoder encoder;

	@Autowired
	private Initialiser initialiser;

	/**
	 * When a particular student registers and data is to saved in the database
	 * 
	 * @param s
	 *            - {@link Students}
	 * @param model
	 * @return registrationStatus
	 */
	public boolean studentRegistration(RegisteredUser registeredUser, ModelMap model) {

		String userName = registeredUser.getUserName();
		String email = registeredUser.getEmail();
		if (dtoOperation.getUserComponent().findOne(userName) == null
				&& dtoOperation.getUserComponent().findByEmail(email) == null) {

			Students student = new Students();
			Users user = new Users();

			user.setUserName(userName);
			student.setUserName(userName);

			user.setFirstName(registeredUser.getFirstName());
			user.setLastName(registeredUser.getLastName());
			user.setEmail(registeredUser.getEmail());
			user.setRole(registeredUser.getRole());

			student.setGuardiansIdGuardians(1);

			// encode the password
			String encodedPassword = encoder.encodePassword(registeredUser.getPassword());

			user.setPassword(encodedPassword);
			user.setEmail(registeredUser.getEmail());
			student.setField(registeredUser.getField());

			// getting the main course list according to registered field
			ArrayList<Courses> mainCourses = coursesService.mainCoursesById(student.getField());

			// default list of experts
			ArrayList<Experts> allExperts = dtoOperation.getExpertsComponents().allExperts();

			// assign a new schedule
			Schedule schedule = new Schedule();

			// Set the messege
			String msg = "Enter the schedule";

			// initialising values for schedule page
			initialiser.schedulerInitialiser(mainCourses, allExperts, schedule, userName, model, msg);

			dtoOperation.getUserComponent().saveUser(user);
			dtoOperation.getStudentsComponents().saveStudent(student);

			return true;
		} else
			return false;
	}

	/**
	 * When a student logs in and the Scheduler has to be set
	 * 
	 * @param l1
	 *            - {@link LoggedUser}
	 * @param model
	 * @return loginStatus
	 */
	public void studentsLogin(LoggedUser l1, ModelMap model) {

		String loginId = l1.getUserName();
		Students student = dtoOperation.getStudentsComponents().findOneStudent(loginId);

		String username = student.getUserName();
		Schedule schedule;

		// set the main course according to the fiels
		ArrayList<Courses> mainCourses = coursesService.minorCoursesById(student.getField());

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

	}

	/**
	 * To get the student list with the username
	 * 
	 * @param userName
	 *            - Students UserName
	 * @return {@link Students}
	 */
	public Students findStudentByUsername(String userName) {
		return dtoOperation.getStudentsComponents().findOneStudent(userName);
	}

	/**
	 * To get the all Student list
	 * 
	 * @return
	 */
	public ArrayList<Students> getAllStudents() {
		return dtoOperation.getStudentsComponents().allStudents();
	}

	/**
	 * To save the student with the courses in join table
	 * 
	 * @param s
	 *            - {@link StudentsHasExperts}
	 */
	public void saveStudentsAndMinorCourse(StudentsHasCourses s) {
		dtoOperation.getStudentsComponents().saveStudentsHasCourses(s);
	}

	/**
	 * To save the student that has experts in join table
	 * 
	 * @param se-
	 *            {@link StudentsHasExperts}s
	 */
	public void saveStudentsHasExperts(StudentsHasExperts se) {
		dtoOperation.getStudentsComponents().saveStudentsHasExperts(se);
	}

}
