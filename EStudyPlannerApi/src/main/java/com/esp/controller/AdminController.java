package com.esp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.esp.service.AdminService;

@Controller
@SessionAttributes({"adminForStudentsList","adminForExpertsList"})
public class AdminController {
	

	/**
	 * To return to admin page
	 * @return
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(ModelMap model) {
		
	return "Admin";
	}
	
	
	@RequestMapping(value = "/adminForExpert", method = RequestMethod.GET)
	public String adminExpertPage(ModelMap model) {
		
	return "AdminForExpert";
	}
	
	
	
	
	
	
	

}
