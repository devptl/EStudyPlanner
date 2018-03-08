package com.esp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esp.model.Schedule;
import com.esp.model.StudentsHasCourses;
import com.esp.service.CoursesService;
import com.esp.service.ScheduleService;

@Controller
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private CoursesService coursesService;

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

		return "Scheduler";

	}

	/**
	 * To add the courses for the Student
	 * 
	 * @param schedule
	 * @param studentsHasCourses
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/entryUserAndMainCourse", method = RequestMethod.POST)
	public String entryStudentWithCourse(@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		
		String username = studentsHasCourses.getStudentsUserName();

		//saving the data in the joint table student has courses
		coursesService.saveUserAndMainCourse(studentsHasCourses);
		Schedule s1 = scheduleService.findSchedule(username);
		scheduleService.saveSchedule(s1, model);

		return "Scheduler";

	}
}
