package com.esp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esp.model.Courses;
import com.esp.service.CoursesService;

@Controller
public class CourseController {

	@Autowired
	private CoursesService coursesService;

	/**
	 * To view all Courses
	 * 
	 * @return {@link Courses}
	 */
	@RequestMapping(value = "allCourses", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Courses> allCourse() {
		ArrayList<Courses> courses;
		courses = coursesService.showCourses();
		return courses;
	}

	/**
	 * To view field courses
	 * 
	 * @return {@link Courses}
	 */
	@RequestMapping(value = "fieldCourses", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Courses> fieldCourse() {
		ArrayList<Courses> courses;
		courses = coursesService.getfieldCourses(0);
		return courses;
	}

	/**
	 * To view main courses
	 * 
	 * @return {@link Courses}
	 */
	@RequestMapping(value = "mainCourses", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Courses> mainCourse() {
		ArrayList<Courses> courses;
		courses = coursesService.mainCourses();
		return courses;
	}

}
