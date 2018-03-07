package com.esp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.Students;
import com.esp.service.CoursesService;
import com.esp.service.ExpertsService;
import com.esp.service.Initialiser;

@Controller
public class ExpertsController {

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private ExpertsService expertsService;

	@Autowired
	private Initialiser initialiser;

	/**
	 * To set the inital display of experts so that the can select field and major
	 * courses
	 * 
	 * @param mainCourse
	 * @param model
	 * @return {@link Experts.html}
	 */
	@RequestMapping(value = "/Experts", method = RequestMethod.GET)
	public String expertDisplay(@ModelAttribute("Courses") Courses mainCourse, ModelMap model) {

		initialiser.expertInitialiser(model);
		return "Experts";
	}

	/**
	 * To display all the available minor courses for expert to make list or set
	 * study material
	 * 
	 * @param mainCourse
	 * @param model
	 * @return {@link Experts.html}
	 */
	@RequestMapping(value = "/ExpertsByMainId", method = RequestMethod.POST)
	public String expertDisplayByMainCourseId(@ModelAttribute("Courses") Courses mainCourse, ModelMap model) {

		// get the main course id to display minor courses
		int id = mainCourse.getIdCourse();

		// major courses
		ArrayList<Courses> mainCourses = null;
		mainCourses = coursesService.mainCourses();

		// minor courses according to major courses
		ArrayList<Courses> minorCourses = null;
		minorCourses = coursesService.mainCoursesById(id);

		initialiser.expertInitialiserWithParameters(mainCourses, minorCourses, model);

		return "Experts";
	}

	/**
	 * This controller is for the Expert registration
	 * 
	 * @param expert
	 * @param student
	 * @param loggedUser
	 * @param model
	 * @return {@link Experts.html}
	 */
	@RequestMapping(value = "/expertsRegistration", method = RequestMethod.POST)
	public String expertRegistrationController(@ModelAttribute("Experts") Experts expert,
			@ModelAttribute("Students") Students student, @ModelAttribute("LoggedUser") LoggedUser loggedUser,
			ModelMap model) {

		if (expertsService.expertsRegistration(expert)) {

			initialiser.expertInitialiser(model);
			return "Expert";
		} else {
			initialiser.frontInitialiser(model);
			return "front";
		}

	}

}
