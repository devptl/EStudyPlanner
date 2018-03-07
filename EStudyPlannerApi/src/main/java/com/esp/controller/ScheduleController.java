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

	@RequestMapping(value = "/enterSchedule", method = RequestMethod.POST)
	public String scheduleEntry(@ModelAttribute("Schedule") Schedule schedule,
								@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses
								,ModelMap model)
	{
		
		scheduleService.saveSchedule(schedule,model);
		
		return "Scheduler";
		
	}
	
	@RequestMapping(value = "/entryUserAndMainCourse", method = RequestMethod.POST)
	public String entryStudentWithCourse(@ModelAttribute("Schedule") Schedule schedule,
										@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses,
										ModelMap model) {
		
		String username=studentsHasCourses.getStudentsUserName();
		
		coursesService.saveUserAndMainCourse(studentsHasCourses);
		Schedule s1=scheduleService.findSchedule(username);
		scheduleService.saveSchedule(s1,model);
		
		return "Scheduler";
		
	}
}
