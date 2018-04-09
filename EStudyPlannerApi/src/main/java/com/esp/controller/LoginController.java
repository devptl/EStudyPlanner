package com.esp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.RegisteredUser;
import com.esp.model.Schedule;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.model.Users;
import com.esp.service.AdminService;
import com.esp.service.Decoder;
import com.esp.service.Initialiser;
import com.esp.service.SMTPMailSender;
import com.esp.service.ScheduleService;
import com.esp.service.UsersService;

@Controller
@SessionAttributes({ "onLoadFun", "onLoadSchedule", "username", "fieldCourses", "mainCourses", "courseforstudymaterial",
		"expschedulemsg", "minorschedulemsg", "user", "minorCourses", "studymaterials", "expertGivenStudyMaterials",
		"addStudyMaterialMessage", "schedule", "message", "allExperts", "button1", "button2", "button3", "button4",
		"div1", "div2", "div3", "div4", "expminormsg", "msg", "shbutton1", "shbutton2", "adminForStudentsList",
		"adminForExpertsList", "shbutton3", "shbutton4", "shdiv1", "shdiv2", "shdiv3", "shdiv4" })
public class LoginController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private SMTPMailSender sMTPMailSender;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private Decoder decoder;

	@Autowired
	private Initialiser initialiser;

	/**
	 * This controller is for the logged user expert or student they are handled
	 * here
	 * 
	 * @param expert
	 * @param student
	 * @param loggedUser
	 * @param mainCourse
	 * @param schedule
	 * @param studentsHasCourses
	 * @param model
	 * @return {@link Experts.html} or {@link Scheduler.html} or {@link Admin.html}
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("RegisteredUser") RegisteredUser registeredUser,
			@ModelAttribute("LoggedUser") LoggedUser loggedUser, @ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		if (usersService.usersLogin(loggedUser)) {

			Users user = usersService.findUser(loggedUser.getUserName());
			if (user.getRole().equals("User")) {
				// initialsing the username for expert
				String userName = loggedUser.getUserName();
				initialiser.expertInitialiser(userName, model);
				
				Schedule schedule2;
				// if schedule already exist
				if (scheduleService.findSchedule(userName) == null) {
					schedule2 = new Schedule();
				} else {
					schedule2 = scheduleService.findSchedule(userName);
				}

				model.addAttribute("schedule", schedule2);		
				model.addAttribute("user", user);

				// on successfull login sending to experts page
				return "Experts";

			} else if (user.getRole().equals("Admin")) {

				// login as admin
				// initialising the admin for students
				ArrayList<Students> adminForStudentsList = adminService.getAdminForStudent();
				model.addAttribute("adminForStudentsList", adminForStudentsList);

				// initialising the admin for experts
				ArrayList<Experts> adminForExpertsList = adminService.getAdminForExperts();
				model.addAttribute("adminForExpertsList", adminForExpertsList);

				return "Admin";
			} else {
				// on invalid login redirecting back to front page
				model.addAttribute("msg", "invalid role");
				return "front";
			}
		} else {

			// on invalid login redirecting back to front page
			model.addAttribute("msg", "Invalid Username or Password");
			return "front";
		}

	}

	/**
	 * To register for both the users
	 * 
	 * @param loggedUser
	 * @param registeredUser
	 * @param mainCourse
	 * @param schedule
	 * @param studentsHasCourses
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("LoggedUser") LoggedUser loggedUser,
			@ModelAttribute("RegisteredUser") RegisteredUser registeredUser,
			@ModelAttribute("Courses") Courses mainCourse, @ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		if (usersService.userRegistration(registeredUser, model)) {
			String userName = registeredUser.getUserName();
			// initalise the username
			initialiser.expertInitialiser(userName, model);

			// sending the mail on registration
			String emailId = registeredUser.getEmail();
			String subject = "Thanku User " + registeredUser.getFirstName() + " for registration";
			String messege = "We are very thankfull for your support your"
					+ " can now set the study material and provide your valuable feeds to us ";

			try {
				sMTPMailSender.send(emailId, subject, messege);
			} catch (Exception e) {
				System.out.println("network problem");
			}

			model.addAttribute("username", userName);
			return "Experts";
		} else {
			
			// on invalid registration when expert already exist with username
			model.addAttribute("msg", "Expert with same username or email alredy exist");
			initialiser.frontInitialiser(model);
			return "front";
		}

	}

	/**
	 * When a particular user forget the paswword a mail will be send only he enters
	 * the correct username and email
	 * 
	 * @param loggedUser
	 * @param expert
	 * @param student
	 * @param userName
	 * @param email
	 * @param role
	 * @param model
	 * @return {@link front.html}
	 */
	@RequestMapping(value = "/forgotThePassword", method = RequestMethod.POST)
	public String forgotThePassword(@ModelAttribute("LoggedUser") LoggedUser loggedUser,
			@ModelAttribute("RegisteredUser") RegisteredUser registeredUser, @RequestParam String userName,
			@RequestParam String email, ModelMap model) {
		String msg = new String();
		try {

			// checking if student exist with username and that email

			if (usersService.checkForUser(userName, email)) {
				// checking if user exist the username and email

				// sending the mail to user with password
				Users user = usersService.findUser(userName);
				String decodedPassword = decoder.decodePassword(user.getPassword());

				msg = "Sending mail to : " + user.getEmail() + " for the Username : " + user.getUserName();

				String text = "Hello " + user.getFirstName()
						+ " we think that you forgot the password your password is : " + decodedPassword;

				sMTPMailSender.send(user.getEmail(), "Forgot the password alert", text);

			} else {
				
				//if the wrong password is given 
				msg = "invalid attempt to get the user details";
				System.out.println("Invalid attempt to get User");
			}

		} catch (Exception e) {
			//if the error occour while sending the mail
			System.out.println("network error");
			model.addAttribute("msg", msg);

		}

		// setting the message for front page
		model.addAttribute("msg", msg);
		return "front";
	}

	/**
	 * When user want to change the password a mail will be send with the registered
	 * mail id containing new password
	 * 
	 * @param loggedUser
	 * @param expert
	 * @param student
	 * @param userName
	 * @param oldPassword
	 * @param newPassword
	 * @param role
	 * @param model
	 * @return {@link front.html}
	 */
	@RequestMapping(value = "/changeThePassword", method = RequestMethod.POST)
	public String changeThePassword(@ModelAttribute("LoggedUser") LoggedUser loggedUser,
			@ModelAttribute("RegisteredUser") RegisteredUser registeredUser, @RequestParam String userName,
			@RequestParam String oldPassword, @RequestParam String newPassword, ModelMap model) {

		String msg = new String();

		try {

			// checking if student exist with username and that email
			if (usersService.changeTheUserPassword(userName, oldPassword, newPassword)) {

				// sending the mail to expert with new password after the change
				Users user = usersService.findUser(userName);
				msg = "Password is changed";
				String text = "Hello " + user.getFirstName() + " your password is changed make sure your do not "
						+ "disclose the password .The new password is : " + newPassword;

				sMTPMailSender.send(user.getEmail(), "Changed the password alert", text);

			} else {
				msg = "invalid attempt to change password";
				System.out.println("Invalid attempt to change Password");
			}

		} catch (Exception e) {
			//is error occour while sending the mail
			System.out.println("network error");
		}

		// setting the message for front page
		model.addAttribute("profileMessage", msg);
		return "Profile";
	}

}
