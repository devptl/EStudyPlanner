package com.esp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.Students;
import com.esp.service.Initialiser;

@Controller
@SessionAttributes({"fieldCourses","msg"})
public class NavController {

	@Autowired
	private Initialiser initialiser;

	/**
	 * To set the front.html as home page
	 * 
	 * @param model
	 * @return {@link front.html}
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homeDisplay(@ModelAttribute("Experts") Experts expert, @ModelAttribute("Students") Students student,
			@ModelAttribute("LoggedUser") LoggedUser loggedUser, ModelMap model) {

		initialiser.frontInitialiser(model);
		return "front";
	}



}
