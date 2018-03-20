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

import com.esp.model.StudentsHasStudyMaterials;
import com.esp.model.StudyMaterials;
import com.esp.service.StudyMaterialsService;

@Controller
@SessionAttributes({ "username", "minorCourse", "studyMaterials", "vediolink", "studentCompletedMaterials",
		"perCompleted","noOfVedios", "shbutton1", "shdiv1" })
public class StudyMaterialsController {

	@Autowired
	private StudyMaterialsService studyMaterialsService;

	/**
	 * To show the vedios in the courses page
	 * 
	 * @param studyMaterialsLink
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showVedios", method = RequestMethod.POST)
	public String showVedios(@RequestParam String studyMaterialsLink, ModelMap model) {

		model.addAttribute("vediolink", studyMaterialsLink);

		return "Courses";

	}

	@RequestMapping(value = "/StudentCompletedMaterials", method = RequestMethod.POST)
	public String studentCompletedMaterials(@RequestParam String studentsUserName,
			@RequestParam int noOfVedios,
			@RequestParam String studyMaterialsList, ModelMap model) {

		String[] studyMaterialId = studyMaterialsList.split(",");

		studyMaterialsService.saveStudentHasStudyMaterials(studyMaterialId, studentsUserName);

		ArrayList<StudentsHasStudyMaterials> studentCompletedMaterials = studyMaterialsService
				.getCompletedList(studentsUserName);
		
		ArrayList<StudyMaterials> studylist = studyMaterialsService
				.getStudyMaterialsForStudent(studentCompletedMaterials);
		
		
		float comp=studentCompletedMaterials.size();
        float perCompleted = (comp*100)/noOfVedios;
        		
        //completed percent initialisation
      	model.addAttribute("perCompleted",perCompleted);
	
		// new completed list initialisation
		model.addAttribute("studentCompletedMaterials", studylist);

		model.addAttribute("shbutton1", "btn btn-link");
		model.addAttribute("shdiv1", "collapse show");

		return "Courses";

	}

}
