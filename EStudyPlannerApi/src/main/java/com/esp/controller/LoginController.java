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

import com.esp.model.AdminForExperts;
import com.esp.model.AdminForStudents;
import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.RegisteredUser;
import com.esp.model.Schedule;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.service.AdminService;
import com.esp.service.ExpertsService;
import com.esp.service.Initialiser;
import com.esp.service.SMTPMailSender;
import com.esp.service.StudentsService;

@Controller
@SessionAttributes({ "onLoadFun", "onLoadSchedule", "username", "fieldCourses", "mainCourses", "courseforstudymaterial",
		"minorCourses", "studymaterials", "expertGivenStudyMaterials", "addStudyMaterialMessage", "schedule", "message",
		"allExperts", "button1", "button2", "button3", "button4", "div1", "div2", "div3", "div4", "msg", "shbutton1",
		"shbutton2", "adminForStudentsList", "adminForExpertsList", "shbutton3", "shbutton4", "shdiv1", "shdiv2",
		"shdiv3", "shdiv4" })
public class LoginController {

	@Autowired
	private StudentsService studentsService;

	@Autowired
	private ExpertsService expertsService;

	@Autowired
	private SMTPMailSender sMTPMailSender;

	@Autowired
	private AdminService adminService;

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
	public String login(@ModelAttribute("Experts") Experts expert, @ModelAttribute("Students") Students student,
			@ModelAttribute("LoggedUser") LoggedUser loggedUser, @ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		if (expertsService.expertsLogin(loggedUser)) {

			// initialsing the username for expert
			String userName = loggedUser.getUserName();
			initialiser.expertInitialiser(userName, model);

			// on successfull login sending to experts page
			return "Experts";

		} 
		else if (studentsService.studentsLogin(loggedUser, model)) {

			// initialise onload function
			model.addAttribute("onLoadSchedule", "scheduleSetting('maincourseselector')");

			// set the toggle as schedule already set
			model.addAttribute("shbutton1", "btn btn-link collapsed");
			model.addAttribute("shdiv1", "collapse");

			model.addAttribute("shbutton2", "btn btn-link ");
			model.addAttribute("shdiv2", "collapse show");

			// on successfull login sending to scheduler page
			return "Scheduler";
		}
		else if (adminService.adminLogin(loggedUser)) {

			// login as admin
			// initialising the admin for students
			ArrayList<AdminForStudents> adminForStudentsList = adminService.getAdminForStudent();
			model.addAttribute("adminForStudentsList", adminForStudentsList);

			// initialising the admin for experts
			ArrayList<AdminForExperts> adminForExpertsList = adminService.getAdminForExperts();
			model.addAttribute("adminForExpertsList", adminForExpertsList);

			return "Admin";
		} else {

			// on invalid login redirecting back to front page
			model.addAttribute("msg", "invalid username or password");
			return "front";
		}

	}

	/**
	 * To register for both the users
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

		if (registeredUser.getRole().equals("Student")) {
			if (studentsService.studentRegistration(registeredUser, model)) {
				// sending the mail to student on registration
				String emailId = registeredUser.getEmail();
				String subject = "Thanku " + registeredUser.getFirstName() + " for registration";
				String messege = "We are very thankfull for your support your"
						+ " can now set your schedule and proceed with your studies ";
				try {

					sMTPMailSender.send(emailId, subject, messege);
				} catch (Exception e) {
					System.out.println("network error");
				}

				// on succesfull registration sending to the scheduler page
				return "Scheduler";
			} else {
				// if the registration fails sending back to the front page
				model.addAttribute("msg", "student with same username or email exist");
				initialiser.frontInitialiser(model);
				return "front";
			}
		} else if (registeredUser.getRole().equals("Expert")) {
			if (expertsService.expertsRegistration(registeredUser, model)) {
				String userName = loggedUser.getUserName();
				// initalise the username
				initialiser.expertInitialiser(userName, model);

				// sending the mail on registration
				String emailId = registeredUser.getEmail();
				String subject = "Thanku Expert " + registeredUser.getFirstName() + " for registration";
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
				model.addAttribute("msg", "expert with same username or email alredy exist");
				initialiser.frontInitialiser(model);
				return "front";
			}
		} else {
			model.addAttribute("msg", "role selecction invalid");
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
			@ModelAttribute("Experts") Experts expert, @ModelAttribute("Students") Students student,
			@RequestParam String userName, @RequestParam String email, ModelMap model) {
		String msg = new String();
		try {

			// checking if student exist with username and that email

			if (expertsService.findOneExpert(userName, email)) {
				// checking if experts exist the username and email

				// sending the mail to expert with password
				Experts e1 = expertsService.findExpertByUsername(userName);
				msg = "Sending mail to : " + e1.getEmail() + " for the Username : " + e1.getUserName();

				String text = "The Expert " + e1.getFirstName()
						+ " we think that you forgot the password your password is " + e1.getPassword();

				sMTPMailSender.send(e1.getEmail(), "Forgot the password alert", text);

			} else if (studentsService.findOneStudent(userName, email)) {

				// sending the mail to student with password
				Students s1 = studentsService.findStudentByUsername(userName);
				msg = "Sending mail to : " + s1.getEmail() + " for the Username : " + s1.getUserName();
				String text = "The user we think that you forgot the password your password is " + s1.getPassword();

				sMTPMailSender.send(s1.getEmail(), "Forgot the password alert", text);
			} else {
				msg = "invalid attempt to get user";
				System.out.println("invalid attempt to get user");
			}

		} catch (Exception e) {
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
			@ModelAttribute("Experts") Experts expert, @ModelAttribute("Students") Students student,
			@RequestParam String userName, @RequestParam String oldPassword, @RequestParam String newPassword,
			ModelMap model) {

		String msg = new String();

		try {

			// checking if student exist with username and that email
			if (expertsService.changeTheExpertPassword(userName, oldPassword, newPassword)) {

				// sending the mail to expert with new password after the change
				Experts e1 = expertsService.findExpertByUsername(userName);
				msg = "experts password is changed";
				String text = "The Expert " + e1.getFirstName() + " your password is changed make sure your do not "
						+ "disclose the password .The new password is " + e1.getPassword();

				sMTPMailSender.send(e1.getEmail(), "Forgot the password alert", text);

			} 
			else if (studentsService.changeTheStudentPassword(userName, oldPassword, newPassword)) {

				// sending the mail to student with new password after the change
				Students s1 = studentsService.findStudentByUsername(userName);
				msg = "student the password changed";

				String text = "The user your password is changed make sure your do not "
						+ "disclose the password .The new password is " + s1.getPassword();

				sMTPMailSender.send(s1.getEmail(), "Forgot the password alert", text);

			} else {
				msg = "invalid attempt to change password";
				System.out.println("invalid attempt to change password");
			}

		} catch (Exception e) {
			System.out.println("network error");
		}

		// setting the message for front page
		model.addAttribute("msg", msg);
		return "front";
	}

}
