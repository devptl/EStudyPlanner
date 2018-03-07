package com.esp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.Schedule;
import com.esp.model.Students;
import com.esp.service.Initialiser;
import com.esp.service.StudentsService;

@Controller
public class StudentsController {

	@Autowired
	private StudentsService studentsService;

	@Autowired
	private Initialiser initialiser;

	/**
	 * For the student registration
	 * 
	 * @param expert
	 * @param student
	 * @param loggedUser
	 * @param model
	 * @return {@link Scheduler.html}
	 */
	@RequestMapping(value = "/studentsRegistration", method = RequestMethod.POST)
	public String studentRegistrationController(@ModelAttribute("Experts") Experts expert,
			@ModelAttribute("Students") Students student, @ModelAttribute("LoggedUser") LoggedUser loggedUser,
			@ModelAttribute("Schedule") Schedule schedule,
			ModelMap model) {

		if (studentsService.studentRegistration(student, model)) {

			return "Scheduler";
		} else {
			initialiser.frontInitialiser(model);
			return "front";
		}

	}

}
