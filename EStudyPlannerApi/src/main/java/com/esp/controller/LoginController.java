package com.esp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.Schedule;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.service.ExpertsService;
import com.esp.service.Initialiser;
import com.esp.service.StudentsService;

@Controller
public class LoginController {

	@Autowired
	private StudentsService studentsService;

	@Autowired
	private ExpertsService expertsService;

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
	 * @return {@link Experts.html} or {@link Scheduler.html}
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("Experts") Experts expert, @ModelAttribute("Students") Students student,
			@ModelAttribute("LoggedUser") LoggedUser loggedUser, @ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		if (loggedUser.getRole() == 1) {
			if (studentsService.studentsLogin(loggedUser, model)) {
				return "Scheduler";
			} else {
				initialiser.frontInitialiser(model);
				return "front";
			}

		} else {
			if (expertsService.expertsLogin(loggedUser)) {

				initialiser.expertInitialiser(model);
				return "Experts";
			} else {
				initialiser.frontInitialiser(model);
				return "front";
			}

		}
	}
}
