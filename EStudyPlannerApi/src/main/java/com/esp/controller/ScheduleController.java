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

import com.esp.model.Schedule;
import com.esp.model.StudentsHasCourses;
import com.esp.model.StudyMaterials;
import com.esp.service.ScheduleService;
import com.esp.service.StudyMaterialsService;

@Controller
@SessionAttributes({"username", "schedule","message","mainCourses",
	"minorCourses","allExperts","courseforstudymaterial",
	"shbutton1","shbutton2","shbutton3","shbutton4","shdiv1","shdiv2","shdiv3","shdiv4"})
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	
	@Autowired
	private StudyMaterialsService studyMaterialsService;
	
	/**
	 * To set the initial display of scheduler page
	 * 
	 * @return {@link Scheduler.html}
	 */
	@RequestMapping(value = "/Scheduler", method = RequestMethod.GET)
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
	 * @return
	 */
	@RequestMapping(value = "/enterSchedule", method = RequestMethod.POST)
	public String scheduleEntry(@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		//checking the difference of timings 
		int diff[] = scheduleService.checkSchedule(schedule);
		
		//if any of the time difference is less than zero
		//schedule wouldn't be saved or updated
		if (diff[0] > 0 && diff[1] > 0 && diff[2] > 0) {
			scheduleService.saveSchedule(schedule, model);
		} else {
			scheduleService.dontSaveSchedule(schedule, model);
		}
		
		model.addAttribute("shbutton1", "btn btn-link collapsed");
		model.addAttribute("shdiv1", "collapse");
			
		model.addAttribute("shbutton2", "btn btn-link");
		model.addAttribute("shdiv2", "collapse show");

		return "Scheduler";

	}
	
	/**
	 * To Show the study material to the student
	 * @param schedule
	 * @param studentsHasCourses
	 * @param courseforstudymaterial
	 * @param expertsUserName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showStudyMaterials", method = RequestMethod.POST)
	public String showStudyMaterials(@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses,
			@RequestParam String courseforstudymaterial,
			@RequestParam String expertsUserName,ModelMap model) {
		
		ArrayList<StudyMaterials> s1=
				studyMaterialsService.showStudyMaterialsByUserNameAndCourseId(courseforstudymaterial, expertsUserName);

		
		model.addAttribute("studyMaterials",s1);
		
		return "Courses";

	}
	

	
}
