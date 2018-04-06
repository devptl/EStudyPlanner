package com.esp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.esp.Component.ExpertsComponents;
import com.esp.Component.StudentsComponents;
import com.esp.Component.UserComponent;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.RegisteredUser;
import com.esp.model.Schedule;
import com.esp.model.Students;
import com.esp.model.Users;

@Service
public class UsersService {

	@Autowired
	private UserComponent userComponent;

	@Autowired
	private ExpertsComponents expertsComponents;

	@Autowired
	private StudentsComponents studentsComponents;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private Encoder encoder;

	/**
	 * To check For the user by email
	 * 
	 * @param userName
	 * @param email
	 * @return
	 */
	public boolean checkForUser(String userName, String email) {

		Users user = userComponent.findOne(userName);

		// to find the experts with particualr username and email
		if (user != null && user.getEmail().equals(email)) {
			return true;
		} else
			return false;

	}

	/**
	 * For the user login
	 * 
	 * @param l1
	 *            - {@link LoggedUser}
	 * @return loginStatus
	 */
	public boolean usersLogin(LoggedUser l1) {

		String userName = l1.getUserName();
		Users user = userComponent.findOne(userName);
		String encodedPassword = encoder.encodePassword(l1.getPassword());

		// checking if expert exist with username and password
		if (user != null && user.getPassword().equals(encodedPassword)) {
			return true;
		} else
			return false;

	}

	/**
	 * When a user want to change the password
	 * 
	 * @param userName
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean changeTheUserPassword(String userName, String oldPassword, String newPassword) {

		Users user = userComponent.findOne(userName);
		String encodedPassword = encoder.encodePassword(oldPassword);

		// checking expert exist with username and password
		if (user != null && user.getPassword().equals(encodedPassword)) {

			// encode the new password
			String newEncodedPassword = encoder.encodePassword(newPassword);

			// saving the new password
			user.setPassword(newEncodedPassword);
			userComponent.saveUser(user);

			return true;
		} else
			return false;

	}

	/**
	 * For registration of particular expert in database
	 * 
	 * @param registeredUser
	 * @param model
	 * @return registrationStatus
	 */
	public boolean userRegistration(RegisteredUser registeredUser, ModelMap model) {

		String userName = registeredUser.getUserName();
		String email = registeredUser.getEmail();

		if (userComponent.findOne(userName) == null && userComponent.findByEmail(email) == null) {

			Users user = new Users();
			Experts expert = new Experts();
			Students student = new Students();

			user.setUserName(userName);
			expert.setUserName(userName);
			student.setUserName(userName);

			// student data
			student.setGuardiansIdGuardians(1);
			student.setField(registeredUser.getField());

			user.setFirstName(registeredUser.getFirstName());
			user.setLastName(registeredUser.getLastName());

			// encode the password
			String encodedPassword = encoder.encodePassword(registeredUser.getPassword());

			// set the user details
			user.setPassword(encodedPassword);
			user.setEmail(registeredUser.getEmail());
			user.setRole(registeredUser.getRole());

			// experts data
			expert.setQualification(registeredUser.getQualification());

			userComponent.saveUser(user);
			expertsComponents.saveExpert(expert);
			studentsComponents.saveStudent(student);
			
			Schedule schedule2;
			// if schedule already exist
			if (scheduleService.findSchedule(userName) == null) {
				schedule2 = new Schedule();
			} else {
				schedule2 = scheduleService.findSchedule(userName);
				// initialise the message
				model.addAttribute("message", "Want To Make Changes");

			}

			model.addAttribute("schedule", schedule2);
			
			model.addAttribute("user", user);
			

			return true;
		} else
			return false;
	}
	
	
	/**
	 * To edit the detials 
	 * @param user
	 * @param student
	 * @param expert
	 */
	public void saveUserDetails(Users user,Students student, Experts expert) {
		userComponent.saveUser(user);
		expertsComponents.saveExpert(expert);
		studentsComponents.saveStudent(student);		
	}

	/**
	 * To check for the user
	 * 
	 * @param userName
	 * @return
	 */
	public Users findUser(String userName) {
		return userComponent.findOne(userName);
	}

}
