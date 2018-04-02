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
import com.esp.model.Experts;
import com.esp.model.ExpertsHasCourses;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.model.LoggedUser;
import com.esp.model.Schedule;
import com.esp.model.StudentsHasCourses;
import com.esp.model.StudyMaterials;
import com.esp.service.CoursesService;
import com.esp.service.ExpertsService;
import com.esp.service.Initialiser;
import com.esp.service.ScheduleService;
import com.esp.service.StudyMaterialsService;

@Controller
@RequestMapping("/Experts")
@SessionAttributes({ "onLoadFun", "vediolink", "username", "fieldCourses", "mainCourses", "minorCourses",
		"expschedulemsg", "minorschedulemsg", "studymaterials", "expertGivenStudyMaterials", "addStudyMaterialMessage",
		"button1", "button2", "button3", "button4", "div1", "courseforstudymaterial", "div2", "div3", "div4",
		"schedule", "message", "allExperts", "shbutton1", "shbutton2", "shbutton3", "shbutton4", "shdiv1", "shdiv2",
		"shdiv3", "shdiv4" })
public class ExpertsController {

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private ExpertsService expertsService;

	@Autowired
	private Initialiser initialiser;

	@Autowired
	private StudyMaterialsService studyMaterialsService;

	@Autowired
	private ScheduleService scheduleService;

	/**
	 * To set the inital display of experts so that the can select field and major
	 * courses
	 * 
	 * @param mainCourse
	 * @param model
	 * @return {@link Experts.html}
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String expertDisplay(@ModelAttribute("Courses") Courses mainCourse, ModelMap model) {

		initialiser.expertInitialiser("sanket", model);
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
	@RequestMapping(value = "/MainId", method = RequestMethod.POST)
	public String expertDisplayByMainCourseId(@ModelAttribute("Courses") Courses mainCourse, ModelMap model) {

		// get the main course id to display minor courses
		int id = mainCourse.getIdCourse();

		// major courses
		ArrayList<Courses> mainCourses = null;
		mainCourses = coursesService.mainCourses();

		// minor courses according to major courses
		ArrayList<Courses> minorCourses = null;
		minorCourses = coursesService.minorCoursesById(id);
		ArrayList<StudyMaterials> studyMaterials = studyMaterialsService.showStudyMaterialsByCourseid(id);

		initialiser.expertInitialiserWithParameters(studyMaterials, model);

		// inital list of minor courses
		model.addAttribute("minorCourses", minorCourses);

		// inital list of major courses
		model.addAttribute("mainCourses", mainCourses);

		// initialise onload function
		model.addAttribute("onLoadFun", "expertsSetting('coursesIdCourse')");

		// initialise the togglers
		model.addAttribute("button1", "btn btn-link collapsed");
		model.addAttribute("div1", "collapse ");

		model.addAttribute("button2", "btn btn-link");
		model.addAttribute("div2", "collapse show");

		return "Experts";
	}

	/**
	 * When a experts select the minor course to add his inputs
	 * 
	 * @param mainCourse
	 * @param expertsHasCourses
	 * @param model
	 * @return {@link Experts.html}
	 */
	@RequestMapping(value = "/MinorCourses", method = RequestMethod.POST)
	public String expertsHasMinorCourses(@ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("ExpertsHasCourses") ExpertsHasCourses expertsHasCourses, ModelMap model) {

		int courseId = expertsHasCourses.getCoursesIdCourse();
		String userName = expertsHasCourses.getExpertsUserName();

		// initalise the couseForStudyMaterial
		String courseForStudyMaterial = coursesService.getCourseWithId(courseId).getCourseName();
		model.addAttribute("courseforstudymaterial", courseForStudyMaterial);

		// initialise the suggested list if already present
		ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials = expertsService
				.expertsHasStudyMAterialWithUsernameAndCouseId(courseForStudyMaterial, userName);

		ArrayList<StudyMaterials> expertGivenStudyMaterials = studyMaterialsService
				.getStudyMaterials(expertsHasStudyMaterials);

		// initialse the suggested list
		model.addAttribute("expertGivenStudyMaterials", expertGivenStudyMaterials);

		ArrayList<StudyMaterials> studyMaterials = studyMaterialsService.showStudyMaterialsByCourseid(courseId);

		initialiser.expertInitialiserWithParameters(studyMaterials, model);

		// initialise the addmsg as empty
		model.addAttribute("addStudyMaterialMessage", "");

		// initialise onload function
		model.addAttribute("onLoadFun", "expertsSetting('inputstudymaterial')");

		// initialise the togglers
		model.addAttribute("button2", "btn btn-link collapsed");
		model.addAttribute("div2", "collapse ");

		model.addAttribute("button3", "btn btn-link");
		model.addAttribute("div3", "collapse show");

		model.addAttribute("button4", "btn btn-link");
		model.addAttribute("div4", "collapse show");

		return "Experts";
	}

	/**
	 * When experts create an list of study material
	 * 
	 * @param expert
	 * @param student
	 * @param loggedUser
	 * @param studyMaterialsList
	 * @param model
	 * @return {@link Experts.html}
	 */
	@RequestMapping(value = "/SuggestedMaterials", method = RequestMethod.POST)
	public String expertsSuggestedMaterials(@ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("LoggedUser") LoggedUser loggedUser, @RequestParam String studyMaterialsList,
			@RequestParam String userName, @RequestParam String courseforstudymaterial, ModelMap model) {

		String[] studyMaterialId = studyMaterialsList.split(",");

		// to save the list of study material suggested by expert
		expertsService.expertsHasStudyMAterials(courseforstudymaterial, studyMaterialId, userName);

		ExpertsHasCourses expertsHasCourses = new ExpertsHasCourses();
		int coursesIdCourse = coursesService.findCourseByName(courseforstudymaterial).getIdCourse();
		expertsHasCourses.setCoursesIdCourse(coursesIdCourse);
		expertsHasCourses.setExpertsUserName(userName);

		expertsService.expertHasCourses(expertsHasCourses);

		// to get the list of studyy material for display
		ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials = expertsService
				.expertsHasStudyMAterialWithUsernameAndCouseId(courseforstudymaterial, userName);

		ArrayList<StudyMaterials> expertGivenStudyMaterials = studyMaterialsService
				.getStudyMaterials(expertsHasStudyMaterials);
		// initialse the suggested list
		model.addAttribute("expertGivenStudyMaterials", expertGivenStudyMaterials);

		// initialise onload function
		model.addAttribute("onLoadFun", "expertsSetting('inputstudymaterial')");

		model.addAttribute("addStudyMaterialMessage", "");

		model.addAttribute("button4", "btn btn-link collapsed");
		model.addAttribute("div4", "collapse ");

		return "Experts";

	}

	/**
	 * When expert add the study material to particualar course in the database
	 * 
	 * @param mainCourse
	 * @param expertsHasCourses
	 * @param courseforstudymaterial
	 * @param title
	 * @param link
	 * @param model
	 * @return {@link Experts.html}
	 */
	@RequestMapping(value = "/addStudyMaterials", method = RequestMethod.POST)
	public String addStudyMaterials(@ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("ExpertsHasCourses") ExpertsHasCourses expertsHasCourses,
			@RequestParam String courseforstudymaterial, @RequestParam String title, @RequestParam String link,
			ModelMap model) {

		Courses courses = coursesService.findCourseByName(courseforstudymaterial);
		int courseId = courses.getIdCourse();

		// converting link to embeded format
		String myLink = studyMaterialsService.getVedioEmbeddedLink(link);

		studyMaterialsService.saveStudyMaterial(courseId, title, myLink);

		ArrayList<StudyMaterials> studyMaterials = studyMaterialsService
				.showStudyMaterialsByCourseName(courseforstudymaterial);
		initialiser.expertInitialiserWithParameters(studyMaterials, model);

		// initialise onload function
		model.addAttribute("onLoadFun", "expertsSetting('vediotitle')");

		model.addAttribute("addStudyMaterialMessage", "Added a new Study Material");

		model.addAttribute("button3", "btn btn-link collapsed");
		model.addAttribute("div3", "collapse ");

		return "Experts";
	}

	/**
	 * To show the vedios in to experts
	 * 
	 * @param mainCourse
	 * @param expertsHasCourses
	 * @param studyMaterialsLink
	 * @param model
	 * @return {@link Experts.html}
	 */
	@RequestMapping(value = "/showExpertVedios", method = RequestMethod.POST)
	public String showExpertVedios(@ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("ExpertsHasCourses") ExpertsHasCourses expertsHasCourses,
			@RequestParam String studyMaterialsLink, ModelMap model) {

		// initialise onload function
		model.addAttribute("onLoadFun", "expertsSetting('vedioselector')");

		// initialise the vedio link
		model.addAttribute("vediolink", studyMaterialsLink);

		return "Experts";
	}

	/**
	 * When an experts login as student
	 * 
	 * @param schedule
	 * @param studentsHasCourses
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/asStudent", method = RequestMethod.POST)
	public String expertsAsStudent(@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, @RequestParam String userName,
			ModelMap model) {

		try {
			Schedule schedule2;

			// checking if the student exist with same username and email
			if (expertsService.expertAsStudent(userName)) {

				initialiser.schedulerInitialiserWithoutParameter(model);

				model.addAttribute("username", userName);

			} else {
				// getting the experts now as student data

				initialiser.schedulerInitialiserWithoutParameter(model);

				// if schedule already exist
				if (scheduleService.findSchedule(userName) == null) {
					schedule2 = new Schedule();
				} else {
					schedule2 = scheduleService.findSchedule(userName);

				}

				model.addAttribute("schedule", schedule2);
				model.addAttribute("username", userName);

			}

		} catch (Exception exception) {
			System.out.println("something went wrong");
		}

		return "Scheduler";
	}

}
