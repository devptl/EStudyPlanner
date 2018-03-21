package com.esp.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.controller.AdminController;
import com.esp.dto.DtoOperation;
import com.esp.model.AdminForExperts;
import com.esp.model.AdminForStudents;
import com.esp.model.Experts;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.model.LoggedUser;
import com.esp.model.Students;
import com.esp.model.StudentsHasStudyMaterials;
import com.esp.model.StudyMaterials;

@Service
public class AdminService {

	@Autowired
	private DtoOperation dtoOperation;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private ExpertsService expertsService;

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private StudyMaterialsService studyMaterialsService;

	/**
	 * For login as an admin
	 * 
	 * @param l1 - {@link LoggedUser}
	 * @return {@link AdminController}
	 */
	public boolean adminLogin(LoggedUser l1) {
		String loginId = l1.getUserName();

		// checking if expert exist with username and password
		if (dtoOperation.getAdminComponents().findOneAdmin(loginId) == null) {
			return false;
		} else {
			if (dtoOperation.getAdminComponents().findOneAdmin(loginId).getPassword().equals(l1.getPassword())) {

				return true;
			}

			return false;
		}

	}

	/**
	 * Return the full detail of the student
	 * 
	 * @return {@link AdminForStudents}
	 */
	public ArrayList<AdminForStudents> getAdminForStudent() {
		ArrayList<AdminForStudents> adminForStudentsList = new ArrayList<AdminForStudents>();

		// getting list of all the students
		ArrayList<Students> students = dtoOperation.getStudentsComponents().allStudents();

		int i;
		for (i = 0; i < students.size(); i++) {

			AdminForStudents obj = new AdminForStudents();

			obj.setStudents(students.get(i));

			// to set the student has courses list
			obj.setCoursesList(coursesService.getCoursesForStudent(students.get(i).getUserName()));

			// to set the expert list for student
			obj.setExpertslist(expertsService.getExpertForStudent(students.get(i).getUserName()));

			// to add the student has study materials
			ArrayList<StudentsHasStudyMaterials> studentCompletedMaterials = studyMaterialsService
					.getCompletedList(students.get(i).getUserName());

			ArrayList<StudyMaterials> studylist = studyMaterialsService
					.getStudyMaterialsForStudent(studentCompletedMaterials);

			obj.setStudyMaterialsList(studylist);

			adminForStudentsList.add(obj);

		}

		return adminForStudentsList;
	}

	/**
	 * To get the details statistics for experts
	 * 
	 * @return {@link AdminForExperts}
	 */
	public ArrayList<AdminForExperts> getAdminForExperts() {
		ArrayList<AdminForExperts> adminForExpertsList = new ArrayList<AdminForExperts>();

		// getting the list of all the experts
		ArrayList<Experts> experts = dtoOperation.getExpertsComponents().allExperts();

		int i;
		for (i = 0; i < experts.size(); i++) {

			AdminForExperts obj = new AdminForExperts();
			Experts e = experts.get(i);

			obj.setExperts(e);

			// to set the experts courses list
			obj.setCoursesList(coursesService.getCoursesForExpert(e.getUserName()));

			// to set the experts list for student
			obj.setStudentsHasExpertsList(expertsService.getStudentsForExpert(e.getUserName()));

			// to add the experts list of study materials
			ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials = null;
			expertsHasStudyMaterials = expertsService.expertsHasStudyMAterialWithUsername(e.getUserName());

			ArrayList<StudyMaterials> expertGivenStudyMaterials = studyMaterialsService
					.getStudyMaterials(expertsHasStudyMaterials);

			obj.setStudyMaterials(expertGivenStudyMaterials);

			adminForExpertsList.add(obj);

		}

		return adminForExpertsList;
	}

}
