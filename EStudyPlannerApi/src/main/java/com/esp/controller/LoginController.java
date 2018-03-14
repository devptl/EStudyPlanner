package com.esp.controller;

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
import com.esp.model.LoggedUser;
import com.esp.model.Schedule;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.service.ExpertsService;
import com.esp.service.Initialiser;
import com.esp.service.SMTPMailSender;
import com.esp.service.StudentsService;

@Controller
@SessionAttributes({"username","fieldCourses", "mainCourses",
	"courseforstudymaterial","minorCourses","studymaterials",
	"expertsHasStudyMaterials","schedule","message","allExperts",
	"button1","button2","button3","div1","div2","div3","msg",
	"shbutton1","shbutton2","shbutton3","shbutton4","shdiv1","shdiv2","shdiv3","shdiv4"})
public class LoginController {

	@Autowired
	private StudentsService studentsService;

	@Autowired
	private ExpertsService expertsService;
	
	@Autowired
	private SMTPMailSender sMTPMailSender;
	
	@Autowired
	private Initialiser initialiser;

	/**
	 * This controller is for the logged user expert or student they are handled
	 * here
	 * 
	 * @param expert
	 * @param student
	 * @param loggedUser
	 * @param mainCourse
	 * @param schedule
	 * @param studentsHasCourses
	 * @param model
	 * @return {@link Experts.html} or {@link Scheduler.html}
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("Experts") Experts expert, @ModelAttribute("Students") Students student,
			@ModelAttribute("LoggedUser") LoggedUser loggedUser, @ModelAttribute("Courses") Courses mainCourse,
			@ModelAttribute("Schedule") Schedule schedule,
			@ModelAttribute("StudentsHasCourses") StudentsHasCourses studentsHasCourses, ModelMap model) {

		if (loggedUser.getRole() == 1) {
			if (studentsService.studentsLogin(loggedUser, model)) {
				return "Scheduler";
			} else {
				
				model.addAttribute("msg", "invalid username or password");
				return "front";
			}

		} else {
			if (expertsService.expertsLogin(loggedUser)) {

				String userName=loggedUser.getUserName();
				initialiser.expertInitialiser(userName,model);
				
				
				return "Experts";
			} else {
				
				model.addAttribute("msg", "invalid username or password");
				return "front";
			}

		}
	}
	
	/**
	 * When a particular user forget the paswword
	 * a mail will be send only he enters the correct username and email
	 * @param loggedUser
	 * @param expert
	 * @param student
	 * @param userName
	 * @param email
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forgotThePassword", method = RequestMethod.POST)
	public String forgotThePassword(@ModelAttribute("LoggedUser") LoggedUser loggedUser,
									@ModelAttribute("Experts") Experts expert,
									@ModelAttribute("Students") Students student,
									@RequestParam String userName,
									@RequestParam String email,
									@RequestParam int role,ModelMap model) {
		String msg=new String();
		try {
	
	  //to check the role
	  if(role==1)
	  {
		  //checking if student exist with username and that email
		  if(studentsService.findOneStudent(userName,email)==null)
		  {
			
		  }
		  else
		  {
			  Students s1=studentsService.findOneStudent(userName,email);
			  msg="Sending mail to : "+s1.getEmail()+" for the Username : "+s1.getUserName();
			  String text="The user we think that you forgot the password your password is "+ s1.getPassword();
			  
			  sMTPMailSender.send(s1.getEmail(),"Forgot the password alert",text);
		  }
		  
	  }
	  else
	  {
		  //checking if experts exist the username and email
		  if(expertsService.findOneExpert(userName,email)==null)
		  {
			  
		  }
		  else
		  {
			  Experts e1=expertsService.findOneExpert(userName,email);
			  msg="Sending mail to : "+e1.getEmail()+" for the Username : "+e1.getUserName();
			  
			  String text="The Expert "+e1.getFirstName()+
					  " we think that you forgot the password your password is "+ e1.getPassword();
			  
			  sMTPMailSender.send(e1.getEmail(),"Forgot the password alert",text);
		  }
			  
			  		  
	  }
		}catch(Exception e)
		{
			msg="invalid attempt to get user"; 
			System.out.println("invalid attempt to get user");
		}
		
	  model.addAttribute("msg", msg);	
	  return "front";
	}
	
	/**
	 * When user want to change the password a mail will be send
	 * with the registered mail id containing new password
	 * @param loggedUser
	 * @param expert
	 * @param student
	 * @param userName
	 * @param oldPassword
	 * @param newPassword
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/changeThePassword", method = RequestMethod.POST)
	public String changeThePassword(@ModelAttribute("LoggedUser") LoggedUser loggedUser,
									@ModelAttribute("Experts") Experts expert,
									@ModelAttribute("Students") Students student,
									@RequestParam String userName,
									@RequestParam String oldPassword,
									@RequestParam String newPassword,
									@RequestParam int role,ModelMap model) {
		
	String msg=new String();
	
	try {
	  //to check the role
	  if(role==1)
	  {
		  //checking if student exist with username and that email
		  if(studentsService.changeTheStudentPassword(userName, oldPassword, newPassword)==null)
		  {
			 
		  }
		  else
		  {
			  Students s1=studentsService.findStudentByUsername(userName);
			  msg="student the password changed"; 
			  
			  String text="The user your password is changed make sure your do not "
			  		+ "disclose the password .The new password is "+ s1.getPassword();
			  
			  sMTPMailSender.send(s1.getEmail(),"Forgot the password alert",text);
			  
			  
		  }
	  }
	  else
	  {
		  //checking if experts exist the username and email
		  if(expertsService.changeTheExpertPassword(userName, oldPassword, newPassword)==null)
		  {
			  
		  }
		  else
		  {
			  Experts e1=expertsService.findExpertByUsername(userName);
			  msg="experts password is changed";
			  String text="The Expert "+e1.getFirstName()+" your password is changed make sure your do not "
				  		+ "disclose the password .The new password is "+ e1.getPassword();
				  
		      sMTPMailSender.send(e1.getEmail(),"Forgot the password alert",text);
				  
			  
		  }
			  	  		  
	  }
	}catch(Exception e)
	{
		msg="invalid attempt to change password"; 
		System.out.println("invalid attempt to change password");
	}
		
	  model.addAttribute("msg", msg);	
	  return "front";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
