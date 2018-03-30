package com.esp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.esp.model.LoggedUser;
import com.esp.model.RegisteredUser;
import com.esp.service.Initialiser;
import com.esp.service.SetTimer;

@Controller
@SessionAttributes({ "fieldCourses", "msg" })
public class NavController {

	@Autowired
	private Initialiser initialiser;

	@Autowired
	private SetTimer setTimer;

	/**
	 * To set the front.html as home page
	 * 
	 * @param model
	 * @return {@link front.html}
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homeDisplay(@ModelAttribute("RegisteredUser") RegisteredUser registeredUser,
			@ModelAttribute("LoggedUser") LoggedUser loggedUser, ModelMap model) {

		// setting the initial display for front page
		initialiser.frontInitialiser(model);
		return "front";
	}

	/**
	 * To set the timer
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/timer", method = RequestMethod.GET)
	@ResponseBody
	public String startTimer() {
		try {
			// starting the timer
			setTimer.runScheduler();
		} catch (Exception e) {
			System.out.println("the thread exception");
		}

		return "timer has started";
	}

}
