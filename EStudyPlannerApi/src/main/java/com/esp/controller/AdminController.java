package com.esp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.esp.model.Experts;
import com.esp.service.AdminService;

/**
 * Controller class to handle the opterations related to the Admin
 * main mapping                  -  /admin
 * adminPage()                   -  /                         -  To return to admin page
 * adminExpertPage()             -  /Expert                   -  To return to admin page for experts
 * adminStudentPage()            -  /Student                  -  To return to admin page for student
 * 
 * @author mindfire
 *
 */
@Controller
@RequestMapping("/admin")
@SessionAttributes({ "adminForStudentsList", "adminForExpertsList" })
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * To return to admin page
	 * 
	 * @return {@link Admin.html}
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String adminPage(ModelMap model) {

		// initialising the admin for experts
		ArrayList<Experts> adminForExpertsList = adminService.getAdminForExperts();
		model.addAttribute("adminForExpertsList", adminForExpertsList);

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

		// initialising the admin for experts
		ArrayList<Experts> adminForExpertsList = adminService.getAdminForExperts();
		model.addAttribute("adminForExpertsList", adminForExpertsList);

		return "AdminForExpert";
	}

	/**
	 * To return to admin page for student
	 * 
	 * @param model
	 * @return {@link Admin.html}
	 */
	@RequestMapping(value = "/Student", method = RequestMethod.GET)
	public String adminStudentPage(ModelMap model) {

		return "Admin";
	}

}
