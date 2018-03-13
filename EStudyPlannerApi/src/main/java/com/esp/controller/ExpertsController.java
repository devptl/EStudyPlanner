package com.esp.controller;

import java.util.ArrayList;

import javax.mail.MessagingException;

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
import com.esp.model.Students;
import com.esp.model.StudyMaterials;
import com.esp.service.CoursesService;
import com.esp.service.ExpertsService;
import com.esp.service.Initialiser;
import com.esp.service.SMTPMailSender;
import com.esp.service.StudyMaterialsService;


@Controller
@SessionAttributes({ "username", "fieldCourses", "mainCourses",
	"minorCourses","studymaterials","expertsHasStudyMaterials",
	"button1","button2","button3","div1","div2","div3"})
public class ExpertsController {

	@Autowired 
	private SMTPMailSender sMTPMailSender;  

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private ExpertsService expertsService;
		
	@Autowired
	private Initialiser initialiser;

	@Autowired
	private StudyMaterialsService studyMaterialsService;

	/**
	 * To set the inital display of experts so that the can select field and major
	 * courses
	 * 
	 * @param mainCourse
	 * @param model
	 * @return {@link Experts.html}
	 */
	@RequestMapping(value = "/Experts", method = RequestMethod.GET)
	public String expertDisplay(@ModelAttribute("Courses") Courses mainCourse, ModelMap model) {

		initialiser.expertInitialiser("sanket",model);
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
	@RequestMapping(value = "/ExpertsByMainId", method = RequestMethod.POST)
	public String expertDisplayByMainCourseId(@ModelAttribute("Courses") Courses mainCourse, ModelMap model) {

		// get the main course id to display minor courses
		int id = mainCourse.getIdCourse();

		// major courses
		ArrayList<Courses> mainCourses = null;
		mainCourses = coursesService.mainCourses();

		// minor courses according to major courses
		ArrayList<Courses> minorCourses = null;
		minorCourses = coursesService.mainCoursesById(id);
		ArrayList<StudyMaterials> studyMaterials = studyMaterialsService.showStudyMaterialsByCourseid(id);

		initialiser.expertInitialiserWithParameters(studyMaterials, model);

		// inital list of minor courses
		model.addAttribute("minorCourses", minorCourses);

		// inital list of major courses
		model.addAttribute("mainCourses", mainCourses);
		
		//initialise the togglers
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
	 * @return
	 */
	@RequestMapping(value = "/ExpertsHasMinorCourses", method = RequestMethod.POST)
	public String expertsHasMinorCourses(@ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("ExpertsHasCourses") ExpertsHasCourses expertsHasCourses, ModelMap model) {

		expertsService.expertHasCourses(expertsHasCourses);
		int id = expertsHasCourses.getCoursesIdCourse();
		String userName=expertsHasCourses.getExpertsUserName();
		
		
		//initalise the couseForStudyMaterial
		String courseForStudyMaterial=coursesService.getCourseWithId(id).getCourseName();
		model.addAttribute("courseforstudymaterial",courseForStudyMaterial);
		
		
		ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials
		=expertsService.expertsHasStudyMAterialWithUsernameAndCouseId(courseForStudyMaterial, userName);
	   //initialse the suggested list			
		model.addAttribute("expertsHasStudyMaterials", expertsHasStudyMaterials);
		

		ArrayList<StudyMaterials> studyMaterials = studyMaterialsService.showStudyMaterialsByCourseid(id);

		initialiser.expertInitialiserWithParameters(studyMaterials, model);
		
		//initialise the togglers
		model.addAttribute("button2", "btn btn-link collapsed");
		model.addAttribute("div2", "collapse ");
					
		model.addAttribute("button3", "btn btn-link");
		model.addAttribute("div3", "collapse show");
		

		return "Experts";
	}

	/**
	 * This controller is for the Expert registration
	 * 
	 * @param expert
	 * @param student
	 * @param loggedUser
	 * @param model
	 * @return {@link Experts.html}
	 * @throws MessagingException 
	 */
	@RequestMapping(value = "/expertsRegistration", method = RequestMethod.POST)
	public String expertRegistrationController(@ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("Experts") Experts expert,
			@ModelAttribute("Students") Students student,
			@ModelAttribute("LoggedUser") LoggedUser loggedUser,
			ModelMap model) {

		if (expertsService.expertsRegistration(expert)) {


			String userName = loggedUser.getUserName();
			// initalise the username
			initialiser.expertInitialiser(userName,model);
			
			String emailId=expert.getEmail();
			String subject="Thanku Expert "+expert.getFirstName()+" for registration";
			String messege="We are very thankfull for your support your"
					+ " can now set the study material and provide your valuable feeds to us ";

			try {
			sMTPMailSender.send(emailId, subject, messege);
			}
			catch(Exception e)
			{
				System.out.println("network problem");
			}
			
			
			model.addAttribute("username", userName);

			return "Experts";
		} else {
			initialiser.frontInitialiser(model);
			return "front";
		}

	}

	/**
	 * When experts create an list of study material
	 * 
	 * @param expert
	 * @param student
	 * @param loggedUser
	 * @param studyMaterialsList
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ExpertsSuggestedMaterials", method = RequestMethod.POST)
	public String expertsSuggestedMaterials(@ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("Experts") Experts expert,
			@ModelAttribute("Students") Students student,
			@ModelAttribute("LoggedUser") LoggedUser loggedUser,
			@RequestParam String studyMaterialsList,
			@RequestParam String userName,
			@RequestParam String courseforstudymaterial,
		 ModelMap model) {

		 String [] studyMaterialId=studyMaterialsList.split(",");     		 
		 
		 expertsService.expertsHasStudyMAterials(courseforstudymaterial,studyMaterialId, userName);
		 
		 ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials
			=expertsService.expertsHasStudyMAterialWithUsernameAndCouseId(courseforstudymaterial, userName);
		 //initialse the suggested list			
			model.addAttribute("expertsHasStudyMaterials", expertsHasStudyMaterials);
		
		 
		return "Experts";

	}

}
