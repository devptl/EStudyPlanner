package com.esp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.esp.model.Courses;
import com.esp.model.ExpertsHasCourses;
import com.esp.model.Schedule;
import com.esp.model.StudentsHasCourses;
import com.esp.service.CoursesService;
import com.esp.service.ExpertsService;
import com.esp.service.StudentsService;

/**
 * Controller class handles the operation related to student
 * main mapping                         -  /Scheduler
 * entryStudentWithMainCourse()         -  /entryUserAndMainCourse    -  To show the courses in a main course
 * entryStudentWithMinorCourse()        -  /entryUserAndMinorCourse   -  When student sets the minor courses
 * 
 * @author mindfire
 *
 */
@Controller
@RequestMapping("/Scheduler")
@SessionAttributes({ "onLoadSchedule", "username", "schedule", "message", "mainCourses", "minorCourses", "allExperts",
		"courseforstudymaterial", "shbutton1", "shbutton2", "shbutton3", "shbutton4", "shdiv1", "shdiv2", "shdiv3",
		"shdiv4" })
public class StudentsController {

	@Autowired
	private StudentsService studentsService;

	@Autowired
	private ExpertsService expertsService;

	@Autowired
	private CoursesService coursesService;

	/**
	 * To show the courses in the particualar main course
	 * 
	 * @param schedule
	 * @param studentsHasCourses
	 * @param model
	 * @return {@link Scheduler.html}
	 */
	@RequestMapping(value = "/entryUserAndMainCourse", method = RequestMethod.POST)
	public String entryStudentWithMainCourse(@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		int id = studentsHasCourses.getCoursesIdCourse();

		// initialising minor course on selection of major course
		ArrayList<Courses> minorCourses = null;
		minorCourses = coursesService.mainCoursesById(id);

		// checking the minor course has study materials in it
		if (minorCourses.isEmpty()) {
			// initialise expert schedule msg
			model.addAttribute("minorschedulemsg", "No minor courses with study materials");
			// initialise expert schedule msg
			model.addAttribute("expschedulemsg", "First select the Minor Course");
		} else {
			model.addAttribute("minorschedulemsg", "");
			// initialise expert schedule msg
			model.addAttribute("expschedulemsg", "");
		}

		model.addAttribute("minorCourses", minorCourses);

		// initialise onload function
		model.addAttribute("onLoadSchedule", "scheduleSetting('minorcourseselector')");

		// setting the togglers
		model.addAttribute("shbutton1", "btn btn-link collapsed");
		model.addAttribute("shdiv1", "collapse");

		model.addAttribute("shbutton2", "btn btn-link collapsed");
		model.addAttribute("shdiv2", "collapse");

		model.addAttribute("shbutton3", "btn btn-link");
		model.addAttribute("shdiv3", "collapse show");

		return "Scheduler";

	}

	/**
	 * When student sets the minor courses
	 * 
	 * @param schedule
	 * @param studentsHasCourses
	 * @param model
	 * @return {@link Scheduler.html}
	 */
	@RequestMapping(value = "/entryUserAndMinorCourse", method = RequestMethod.POST)
	public String entryStudentWithMinorCourse(@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		// saving the student with minor course in database
		int id = studentsHasCourses.getCoursesIdCourse();
		studentsService.saveStudentsAndMinorCourse(studentsHasCourses);

		Courses minorCourse = coursesService.getCourseWithId(id);

		// initalise the couses for studymaterial
		String courseForStudyMaterial = minorCourse.getCourseName();
		model.addAttribute("courseforstudymaterial", courseForStudyMaterial);

		// initalise new experts list
		ArrayList<ExpertsHasCourses> allExperts = expertsService.findExpertsWithCoursesId(id);
		if (allExperts.isEmpty()) {
			allExperts = new ArrayList<>();
			// initialise expert schedule msg
			model.addAttribute("expschedulemsg", "no one gave feeds till now");
		}
		model.addAttribute("allExperts", allExperts);

		// initialise onload function
		model.addAttribute("onLoadSchedule", "scheduleSetting('expertselector')");

		// setting the togglers
		model.addAttribute("shbutton1", "btn btn-link collapsed");
		model.addAttribute("shdiv1", "collapse");

		model.addAttribute("shbutton3", "btn btn-link collapsed");
		model.addAttribute("shdiv3", "collapse");

		model.addAttribute("shbutton4", "btn btn-link");
		model.addAttribute("shdiv4", "collapse show");

		return "Scheduler";

	}

}
