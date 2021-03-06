package com.esp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.esp.model.Courses;
import com.esp.model.ExpertsHasCourses;
import com.esp.model.StudentsHasStudyMaterials;
import com.esp.model.StudyMaterials;
import com.esp.service.CoursesService;
import com.esp.service.ExpertsService;
import com.esp.service.StudyMaterialsService;

/**
 * Controller class handle the operation related to study materials 
 * main mapping                          -  /StudyMaterials
 * studyMaterials()                      -  /**                         -  To get to Study Materials  
 * showVedios()                          -  /showVedios                 -  To show the vedios in the courses page
 * studentCompletedMaterials()           -  /StudentCompletedMaterials  -  When a student completes the study material
 * getRating()                           -  /getRating                  -  To Store the rating
 * 
 * @author mindfire
 *
 */
@Controller
@RequestMapping("/StudyMaterials")
@SessionAttributes({ "onLoadCourses", "username", "minorCourse", "studyMaterials", "vediolink", "expertsUserName",
		"studentCompletedMaterials", "perCompleted", "noOfVedios", "shbutton1", "shdiv1" })
public class StudyMaterialsController {

	@Autowired
	private StudyMaterialsService studyMaterialsService;

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private ExpertsService expertsService;

	/**
	 * To get to Study Materials
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String studyMaterials(ModelMap model) {
		return "Courses";
	}

	/**
	 * To show the vedios in the courses page
	 * 
	 * @param studyMaterialsLink
	 * @param model
	 * @return {@link Courses.html}
	 */
	@RequestMapping(value = "/showVedios", method = RequestMethod.POST)
	public String showVedios(@RequestParam String studyMaterialsLink, ModelMap model) {

		// initialise onload function
		model.addAttribute("onLoadCourses", "courseSetting('vedioselector')");

		// initialising the vediolink
		model.addAttribute("vediolink", studyMaterialsLink);

		return "Courses";

	}

	/**
	 * When a student completes the study material and add it to the list
	 * 
	 * @param studentsUserName
	 * @param noOfVedios
	 * @param studyMaterialsList
	 * @param model
	 * @return {@link Courses.html}
	 */
	@RequestMapping(value = "/StudentCompletedMaterials", method = RequestMethod.POST)
	public String studentCompletedMaterials(@RequestParam String studentsUserName, @RequestParam int noOfVedios,
			@RequestParam String studyMaterialsList, @RequestParam String courseforstudymaterial, ModelMap model) {

		String[] studyMaterialId = studyMaterialsList.split(",");

		// saving the list to the student has study materials
		studyMaterialsService.saveStudentHasStudyMaterials(studyMaterialId, studentsUserName, courseforstudymaterial);

		ArrayList<StudentsHasStudyMaterials> studentCompletedMaterials = studyMaterialsService
				.getCompletedList(studentsUserName, courseforstudymaterial);

		// getting the new list of completed study materials
		ArrayList<StudyMaterials> studylist = studyMaterialsService
				.getStudyMaterialsForStudent(studentCompletedMaterials);

		// setting the percent
		float comp = studentCompletedMaterials.size();
		float perCompleted = 0;

		try {
			perCompleted = (comp * 100) / noOfVedios;
		} catch (ArithmeticException arithmeticException) {
			perCompleted = 0;
		}

		// completed percent initialisation
		model.addAttribute("perCompleted", perCompleted);

		// new completed list initialisation
		model.addAttribute("studentCompletedMaterials", studylist);

		// initialise onload function
		model.addAttribute("onLoadCourses", "courseSetting('inputstudymaterial')");

		model.addAttribute("shbutton1", "btn btn-link");
		model.addAttribute("shdiv1", "collapse show");

		return "Courses";

	}

	/**
	 * To Store the rating
	 * 
	 * @param rating
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getRating", method = RequestMethod.POST)
	public String getRating(@RequestParam String studentsUserName, @RequestParam String expertsUserName,
			@RequestParam String courseforstudymaterial, @RequestParam int rating, ModelMap model) {

		if (studentsUserName.equalsIgnoreCase(expertsUserName)) {
			// giving the rating to himself not possible
		} else {
			int coursesIdCourse = coursesService.findCourseByName(courseforstudymaterial).getIdCourse();

			// getting the required expert details
			ExpertsHasCourses expertsHasCourses = expertsService.findByExpertAndCourse(expertsUserName,
					coursesIdCourse);

			// setting the rating
			rating = rating + expertsHasCourses.getRating();
			expertsHasCourses.setRating(rating);
			expertsService.expertHasCourses(expertsHasCourses);
		}

		return "Courses";

	}

}
