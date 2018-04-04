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
import com.esp.model.Schedule;
import com.esp.model.StudentsHasCourses;
import com.esp.model.StudentsHasExperts;
import com.esp.model.StudentsHasStudyMaterials;
import com.esp.model.StudyMaterials;
import com.esp.service.ScheduleService;
import com.esp.service.StudentsService;
import com.esp.service.StudyMaterialsService;

@Controller
@RequestMapping("/Scheduler")
@SessionAttributes({ "onLoadSchedule", "username", "schedule", "message", "mainCourses", "minorCourses", "allExperts",
		"expschedulemsg", "minorschedulemsg", "courseforstudymaterial", "minorCourse", "studyMaterials", "vediolink",
		"shbutton1", "shbutton2", "shbutton3", "shbutton4", "studentCompletedMaterials", "noOfVedios", "perCompleted",
		"shdiv1", "shdiv2", "shdiv3", "shdiv4" })
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private StudyMaterialsService studyMaterialsService;

	@Autowired
	private StudentsService studentsService;

	/**
	 * To set the initial display of scheduler page
	 * 
	 * @return {@link Scheduler.html}
	 */
	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String schedulerDisplay(@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		return "Scheduler";
	}
	

	/**
	 * To save the schedule when particular save has to be their
	 * 
	 * @param schedule
	 * @param studentsHasCourses
	 * @param model
	 * @return {@link Scheduler.html}
	 */
	@RequestMapping(value = "/enterSchedule", method = RequestMethod.POST)
	public String scheduleEntry(@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		// checking the difference of timings
		int diff[] = scheduleService.checkSchedule(schedule);

		// if any of the time difference is less than zero
		// schedule wouldn't be saved or updated
		if (diff[0] > 0 && diff[1] > 0 && diff[2] > 0) {
			scheduleService.saveSchedule(schedule, model);
		} else {
			scheduleService.dontSaveSchedule(schedule, model);
		}

		// setting the togglers

		model.addAttribute("shbutton2", "btn btn-link");
		model.addAttribute("shdiv2", "collapse show");

		return "Scheduler";

	}

	/**
	 * To Show the study material to the student
	 * 
	 * @param schedule
	 * @param studentsHasCourses
	 * @param courseforstudymaterial
	 * @param expertsUserName
	 * @param model
	 * @return {@link Courses.html}
	 */
	@RequestMapping(value = "/showStudyMaterials", method = RequestMethod.POST)
	public String showStudyMaterials(@ModelAttribute("Schedule") Schedule schedule,

			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses,
			@RequestParam String studentsUserName, @RequestParam String courseforstudymaterial,
			@RequestParam String expertsUserName, ModelMap model) {

		// initialising the study material for display
		ArrayList<StudyMaterials> studyMaterial;
		StudentsHasExperts se = new StudentsHasExperts(studentsUserName, expertsUserName);

		if (expertsUserName.equals("default")) {
			// if the student select i m my expert
			studyMaterial = studyMaterialsService.showStudyMaterialsByCourseName(courseforstudymaterial);
			if (studyMaterial.isEmpty()) {
				model.addAttribute("expschedulemsg", "no study material");
				return "Scheduler";
			}

		} else {
			// if the student select some other expert
			studyMaterial = studyMaterialsService.showStudyMaterialsByUserNameAndCourseId(courseforstudymaterial,
					expertsUserName);
		}

		// saving the experts with student in student has experts
		studentsService.saveStudentsHasExperts(se);

		ArrayList<StudentsHasStudyMaterials> studentCompletedMaterials = studyMaterialsService
				.getCompletedList(studentsUserName, courseforstudymaterial);

		ArrayList<StudyMaterials> studylist = studyMaterialsService
				.getStudyMaterialsForStudent(studentCompletedMaterials);

		float perCompleted = studyMaterialsService.trackCourseCompletion(studyMaterial, studentCompletedMaterials);

		// completed percent initialisation
		model.addAttribute("perCompleted", perCompleted);

		// initialise onload function
		model.addAttribute("onLoadCourses", "courseSetting('vedioselector')");

		// completed percent initialisation
		model.addAttribute("noOfVedios", studyMaterial.size());

		// completed list initialisation
		model.addAttribute("studentCompletedMaterials", studylist);

		// setting the maincourse and study material for display
		model.addAttribute("minorCourse", courseforstudymaterial);
		// setting the study materials
		model.addAttribute("studyMaterials", studyMaterial);
		// default vedio to be shown
		model.addAttribute("vediolink", studyMaterial.get(0).getStudyMaterialLink());

		return "Courses";

	}

}
