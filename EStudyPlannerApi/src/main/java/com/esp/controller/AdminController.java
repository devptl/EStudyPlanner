package com.esp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/admin")
@SessionAttributes({ "adminForStudentsList", "adminForExpertsList" })
public class AdminController {

	/**
	 * To return to admin page
	 * 
	 * @return {@link Admin.html}
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String adminPage(ModelMap model) {

		return "Admin";
	}

	/**
	 * To return to admin page for experts
	 * 
	 * @param model
	 * @return {@link AdminForExpert.html}
	 */
	@RequestMapping(value = "/Expert", method = RequestMethod.GET)
	public String adminExpertPage(ModelMap model) {

		return "AdminForExpert";
	}
	
	/**
	 * To return to admin page for student
	 * 
	 * @param model
	 * @return {@link AdminForExpert.html}
	 */
	@RequestMapping(value = "/Student", method = RequestMethod.GET)
	public String adminStudentPage(ModelMap model) {

		return "Admin";
	}

}
