package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.Schedule;

@Service
public class Initialiser {

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private ExpertsService expertsService;

	/**
	 * This methord is to initialise the values for the front page
	 * 
	 * @param model
	 */
	public void frontInitialiser(ModelMap model) {

		//initalise the field courses
		ArrayList<Courses> fieldCourses = null;
		fieldCourses = coursesService.fieldCourses();
		model.addAttribute("fieldCourses", fieldCourses);

	}

	/**
	 * This method is to initialise the starting values for experts page
	 * 
	 * @param model
	 */
	public void expertInitialiser(ModelMap model) {

		// intial list of fields
		ArrayList<Courses> fieldCourses = null;
		fieldCourses = coursesService.fieldCourses();
		model.addAttribute("fieldCourses", fieldCourses);

		// inital list of major courses
		ArrayList<Courses> mainCourses = null;
		mainCourses = coursesService.mainCourses();
		model.addAttribute("mainCourses", mainCourses);

		// inital list of minor courses
		ArrayList<Courses> minorCourses = null;
		minorCourses = coursesService.mainCourses();
		model.addAttribute("minorCourses", minorCourses);
	}

	/**
	 * This method is to initialise the values once the expert select the courses
	 * 
	 * @param mainCourses
	 * @param minorCourses
	 * @param model
	 */
	public void expertInitialiserWithParameters(ArrayList<Courses> mainCourses, ArrayList<Courses> minorCourses,
			ModelMap model) {
		//initalise the field courses
		ArrayList<Courses> fieldCourses = null;
		fieldCourses = coursesService.fieldCourses();
		model.addAttribute("fieldCourses", fieldCourses);

		// inital list of major courses
		model.addAttribute("mainCourses", mainCourses);

		// inital list of minor courses
		model.addAttribute("minorCourses", minorCourses);
	}

	/**
	 * This method is to initialise the start values in Scheduler page
	 * 
	 * @param model
	 */
	public void schedulerInitialiserWithoutParameter(ModelMap model) {

		// inital list of major courses
		ArrayList<Courses> mainCourses = null;
		mainCourses = coursesService.mainCourses();
		model.addAttribute("mainCourses", mainCourses);
		
		//initialise the message
		String msg="";
		model.addAttribute("message", msg);
		
		//initalise the username
		String username="";
		model.addAttribute("username", username);
		
		//initalise the schedule
		Schedule schedule=new Schedule();
		model.addAttribute("schedule", schedule);

		// inital list of experts
		ArrayList<Experts> allExperts = null;
		allExperts = expertsService.getAllExperts();
		model.addAttribute("allExperts", allExperts);

	}

	/**
	 * This method initialise the scheduler page once logged in
	 * 
	 * @param mainCourses
	 * @param allExperts
	 * @param model
	 */
	public void schedulerInitialiser(ArrayList<Courses> mainCourses, ArrayList<Experts> allExperts,
			Schedule schedule,String username, ModelMap model,String message) {

		// inital list of major courses
		model.addAttribute("mainCourses", mainCourses);
	
		//initalise the username
		model.addAttribute("username", username);

		// inital list of experts
		model.addAttribute("allExperts", allExperts);
		
		//initalise the schedule
		model.addAttribute("schedule", schedule);
		
		//initialise the message
		model.addAttribute("message", message);

	}

}
