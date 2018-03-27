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
		if (dtoOperation.getStudentsComponents().findOneStudent(userName) == null
				&& dtoOperation.getStudentsComponents().findByEmail(email) == null) {

			Students student = new Students();

			student.setFirstName(registeredUser.getFirstName());
			student.setLastName(registeredUser.getLastName());
			student.setUserName(registeredUser.getUserName());
			student.setGuardiansIdGuardians(1);

			// encode the password
			String encodedPassword = encoder.encodePassword(registeredUser.getPassword());

			student.setPassword(encodedPassword);
			student.setEmail(registeredUser.getEmail());
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
	public boolean studentsLogin(LoggedUser l1, ModelMap model) {

		String loginId = l1.getUserName();
		Students student = dtoOperation.getStudentsComponents().findOneStudent(loginId);
		if (student == null) {
			return false;
		} else {
			String encodedPassword = encoder.encodePassword(l1.getPassword());

			if (student.getPassword().equals(encodedPassword)) {

				String username = student.getUserName();
				Schedule schedule;

				// set the main course according to the fiels
				ArrayList<Courses> mainCourses = coursesService.mainCoursesById(student.getField());

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
	 * @param userName
	 *            - Students UserName
	 * @return {@link Students}
	 */
	public Students findStudentByUsername(String userName) {
		return dtoOperation.getStudentsComponents().findOneStudent(userName);
	}

	/**
	 * To find a student with particular username
	 * 
	 * @param userName
	 *            - Students UserName
	 * @param email
	 * @return studentExistStatus
	 */
	public boolean checkForStudent(String userName, String email) {

		Students student = dtoOperation.getStudentsComponents().findOneStudent(userName);

		// checking if student exist
		return student == null ? false : student.getEmail().equals(email);
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
		Students student = dtoOperation.getStudentsComponents().findOneStudent(userName);
		if (student == null) {
			return false;
		} else {
			String encodedPassword = encoder.encodePassword(oldPassword);

			if (student.getPassword().equals(encodedPassword)) {

				// setting the new password
				// encode new password
				String newEncodedPassword = encoder.encodePassword(newPassword);

				student.setPassword(newEncodedPassword);
				dtoOperation.getStudentsComponents().saveStudent(student);
				return true;
			}
			return false;
		}

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
