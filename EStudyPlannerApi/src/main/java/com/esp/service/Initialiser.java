package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.model.Schedule;
import com.esp.model.StudyMaterials;

@Service
public class Initialiser {

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private ExpertsService expertsService;
	
	@Autowired
	private StudyMaterialsService studyMaterialsService;

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
		
		String msg="";
		model.addAttribute("msg", msg);
		

	}

	/**
	 * This method is to initialise the starting values for experts page
	 * 
	 * @param model
	 */
	public void expertInitialiser(String userName,ModelMap model) {
		
		//initalise the username
		model.addAttribute("username",userName);
		
		// intial list of fields
		ArrayList<Courses> fieldCourses = null;
		fieldCourses = coursesService.fieldCourses();
		model.addAttribute("fieldCourses", fieldCourses);
		
		//initalise the couseForStudyMaterial
		String courseForStudyMaterial="";
		model.addAttribute("courseforstudymaterial",courseForStudyMaterial);
		
		//initalise study materials
		ArrayList<StudyMaterials> studyMaterials=null;
		studyMaterials=studyMaterialsService.allStudyMaterials();
		model.addAttribute("studymaterials",studyMaterials);

		// inital list of major courses
		ArrayList<Courses> mainCourses = null;
		mainCourses = coursesService.mainCourses();
		model.addAttribute("mainCourses", mainCourses);

		// inital list of minor courses
		ArrayList<Courses> minorCourses = null;
		minorCourses = coursesService.mainCourses();
		model.addAttribute("minorCourses", minorCourses);
		
		//initialse the suggested list
		ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials=null;
		expertsHasStudyMaterials=expertsService.expertsHasStudyMAterialWithUsername(userName);
		model.addAttribute("expertsHasStudyMaterials", expertsHasStudyMaterials);
		
		//initialise the togglers
		model.addAttribute("button1", "btn btn-link");
		model.addAttribute("div1", "collapse show");
	
		model.addAttribute("button2", "btn btn-link collapsed");
		model.addAttribute("div2", "collapse");
		
		model.addAttribute("button3", "btn btn-link collapsed");
		model.addAttribute("div3", "collapse");
		
		
		
	}

	
	public void expertInitialiserWithParameters( ArrayList<StudyMaterials> studyMaterials,ModelMap model) {
		
		
		
		//initalise study materials
		model.addAttribute("studymaterials",studyMaterials);
		
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
		
		//initalise the minor courses
		ArrayList<Courses> minorCourses = null;
		minorCourses = coursesService.mainCourses();
		model.addAttribute("minorCourses", minorCourses);
		
		//initalise the couseForStudyMaterial
		String courseForStudyMaterial="";
		model.addAttribute("courseforstudymaterial",courseForStudyMaterial);
			
		
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
		
		//initalise the minor courses
		ArrayList<Courses> minorCourses = null;
		minorCourses = coursesService.mainCourses();
		model.addAttribute("minorCourses", minorCourses);
	
		//initalise the username
		model.addAttribute("username", username);

		// inital list of experts
		model.addAttribute("allExperts", allExperts);
		
		//initalise the schedule
		model.addAttribute("schedule", schedule);
		
		//initialise the message
		model.addAttribute("message", message);
		
		//initialise the togglers
		model.addAttribute("shbutton1", "btn btn-link");
		model.addAttribute("shdiv1", "collapse show");
			
		model.addAttribute("shbutton2", "btn btn-link collapsed");
		model.addAttribute("shdiv2", "collapse");
				
		model.addAttribute("shbutton3", "btn btn-link collapsed");
		model.addAttribute("shdiv3", "collapse");

		model.addAttribute("shbutton4", "btn btn-link collapsed");
		model.addAttribute("shdiv4", "collapse");
	}

}
