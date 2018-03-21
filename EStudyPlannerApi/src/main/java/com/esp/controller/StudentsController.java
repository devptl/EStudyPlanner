package com.esp.controller;

import java.util.ArrayList;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.Schedule;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.service.CoursesService;
import com.esp.service.ExpertsService;
import com.esp.service.Initialiser;
import com.esp.service.SMTPMailSender;
import com.esp.service.StudentsService;

@Controller
@SessionAttributes({ "onLoadSchedule", "username", "schedule", "message", "mainCourses", "minorCourses", "allExperts",
		"courseforstudymaterial", "shbutton1", "shbutton2", "shbutton3", "shbutton4", "shdiv1", "shdiv2", "shdiv3",
		"shdiv4" })
public class StudentsController {

	@Autowired
	private StudentsService studentsService;

	@Autowired
	private ExpertsService expertsService;

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private SMTPMailSender sMTPMailSender;

	@Autowired
	private Initialiser initialiser;

	/**
	 * For the student registration
	 * 
	 * @param expert
	 * @param student
	 * @param loggedUser
	 * @param model
	 * @return {@link Scheduler.html}
	 * @throws MessagingException
	 */
	@RequestMapping(value = "/studentsRegistration", method = RequestMethod.POST)
	public String studentRegistrationController(@ModelAttribute("Experts") Experts expert,
			@ModelAttribute("Students") Students student, @ModelAttribute("LoggedUser") LoggedUser loggedUser,
			@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		if (studentsService.studentRegistration(student, model)) {

			// sending the mail to student on registration
			String emailId = student.getEmail();
			String subject = "Thanku " + student.getFirstName() + " for registration";
			String messege = "We are very thankfull for your support your"
					+ " can now set your schedule and proceed with your studies ";
			try {

				sMTPMailSender.send(emailId, subject, messege);
			} catch (Exception e) {
				System.out.println("network error");
			}

			// on succesfull registration sending to the scheduler page
			return "Scheduler";
		} else {

			// if the registration fails sending back to the front page
			model.addAttribute("msg", "student with same username exist");
			initialiser.frontInitialiser(model);
			return "front";
		}

	}

	/**
	 * To show the courses in the particualar main course
	 * 
	 * @param schedule
	 * @param studentsHasCourses
	 * @param model
	 * @return {@link Scheduler.html}
	 */
	@RequestMapping(value = "/entryUserAndMainCourse", method = RequestMethod.POST)
	public String entryStudentWithMainCourse(@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		int id = studentsHasCourses.getCoursesIdCourse();

		// initialising minor course on selection of major course
		ArrayList<Courses> minorCourses = null;
		minorCourses = coursesService.mainCoursesById(id);
		model.addAttribute("minorCourses", minorCourses);

		// initialise onload function
		model.addAttribute("onLoadSchedule", "scheduleSetting('minorcourseselector')");

		// setting the togglers
		model.addAttribute("shbutton1", "btn btn-link collapsed");
		model.addAttribute("shdiv1", "collapse");

		model.addAttribute("shbutton2", "btn btn-link collapsed");
		model.addAttribute("shdiv2", "collapse");

		model.addAttribute("shbutton3", "btn btn-link");
		model.addAttribute("shdiv3", "collapse show");

		return "Scheduler";

	}

	/**
	 * When student sets the minor courses
	 * 
	 * @param schedule
	 * @param studentsHasCourses
	 * @param model
	 * @return {@link Scheduler.html}
	 */
	@RequestMapping(value = "/entryUserAndMinorCourse", method = RequestMethod.POST)
	public String entryStudentWithMinorCourse(@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		// saving the student with minor course in database
		int id = studentsHasCourses.getCoursesIdCourse();
		studentsService.saveStudentsAndMinorCourse(studentsHasCourses);

		Courses minorCourse = coursesService.getCourseWithId(id);

		// initalise the couses for studymaterial
		String courseForStudyMaterial = minorCourse.getCourseName();
		model.addAttribute("courseforstudymaterial", courseForStudyMaterial);

		// initalise new experts list
		ArrayList<Experts> allExperts = expertsService.findExpertsWithCoursesId(id);
		model.addAttribute("allExperts", allExperts);

		// initialise onload function
		model.addAttribute("onLoadSchedule", "scheduleSetting('expertselector')");

		// setting the togglers
		model.addAttribute("shbutton1", "btn btn-link collapsed");
		model.addAttribute("shdiv1", "collapse");

		model.addAttribute("shbutton3", "btn btn-link collapsed");
		model.addAttribute("shdiv3", "collapse");

		model.addAttribute("shbutton4", "btn btn-link");
		model.addAttribute("shdiv4", "collapse show");

		return "Scheduler";

	}

}
