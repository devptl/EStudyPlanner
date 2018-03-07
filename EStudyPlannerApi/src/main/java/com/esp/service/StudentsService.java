package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.esp.dto.DtoOperation;
import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.Students;

@Service
public class StudentsService {

	@Autowired
	private DtoOperation dtoOperation;

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private Initialiser initialiser;

	/**
	 * When a particular student registers and data is to saved in the database
	 * 
	 * @param s
	 * @param model
	 * @return registrationStatus
	 */
	public boolean studentRegistration(Students s, ModelMap model) {
		String id = s.getUserName();
		if (dtoOperation.getComponents().findOneStudent(id) == null) {
			s.setGuardiansIdGuardians(1);
			String username=s.getUserName();

			ArrayList<Courses> mainCourses = coursesService.mainCoursesById(s.getField());

			ArrayList<Experts> allExperts = dtoOperation.getComponents().allExperts();

			initialiser.schedulerInitialiser(mainCourses, allExperts,username, model);

			dtoOperation.getComponents().saveStudent(s);

			return true;
		} else
			return false;
	}

	/**
	 * When a student logs in and the Scheduler has to be set
	 * 
	 * @param l1
	 * @param model
	 * @return loginStatus
	 */
	public boolean studentsLogin(LoggedUser l1, ModelMap model) {

		String loginId = l1.getUserName();
		if (dtoOperation.getComponents().findOneStudent(loginId) == null) {
			return false;
		} else {
			if (dtoOperation.getComponents().findOneStudent(loginId).getPassword().equals(l1.getPassword())) {
				Students s1 = dtoOperation.getComponents().findOneStudent(loginId);
				String username=s1.getUserName();
				
				ArrayList<Courses> mainCourses = coursesService.mainCoursesById(s1.getField());

				ArrayList<Experts> allExperts = dtoOperation.getComponents().allExperts();

				initialiser.schedulerInitialiser(mainCourses,allExperts,username, model);

				return true;
			}

			return false;
		}

	}

}
