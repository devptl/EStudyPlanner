package com.esp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.esp.model.Experts;
import com.esp.model.Students;
import com.esp.model.Users;
import com.esp.service.ExpertsService;
import com.esp.service.StudentsService;
import com.esp.service.UsersService;

@Controller
@SessionAttributes({ "fieldCourses", "profileMessage", "user" ,"schedule"})
public class ProfileController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private StudentsService studentsService;

	@Autowired
	private ExpertsService expertsService;
	
	/**
	 * To get to the profile of the user
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
	public String getProfile(ModelMap model) {

		model.addAttribute("profileMessage", "");
		return "Profile";

	}

	/**
	 * To update the profile
	 * 
	 * @return
	 */
	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String editProfile(@RequestParam String userName, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam int field, @RequestParam String qualification,
			ModelMap model) {
		
		Users user = usersService.findUser(userName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		
		Students student = studentsService.findStudentByUsername(userName);
		student.setField(field);
		
		Experts expert = expertsService.findExpertByUsername(userName);
		expert.setQualification(qualification);
		
		usersService.saveUserDetails(user, student, expert);
		
		model.addAttribute("profileMessage", "Profile is Updated");
		model.addAttribute("user", user);

		return "Profile";

	}

}
