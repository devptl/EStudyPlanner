package com.esp.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.dto.DtoOperation;
import com.esp.model.Experts;
import com.esp.model.ExpertsHasCourses;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.model.LoggedUser;
import com.esp.model.Students;
import com.esp.model.StudentsHasExperts;

@Service
public class ExpertsService {

	@Autowired
	private DtoOperation dtoOperation;

	@PersistenceContext
	EntityManager entityManager;

	/**
	 * For registration of particular expert in database
	 * 
	 * @param e
	 *            - {@link Experts}
	 * @return registrationStatus
	 */
	public boolean expertsRegistration(Experts e) {
		String id = e.getUserName();

		// checking is user already exist if not registering expert
		if (dtoOperation.getExpertsComponents().findOneExpert(id) == null) {
			dtoOperation.getExpertsComponents().saveExpert(e);
			return true;
		} else
			return false;
	}

	/**
	 * When a particular experts logs in with a specific username
	 * 
	 * @param l1
	 *            - {@link LoggedUser}
	 * @return loginStatus
	 */
	public boolean expertsLogin(LoggedUser l1) {

		String loginId = l1.getUserName();

		// checking if expert exist with username and password
		if (dtoOperation.getExpertsComponents().findOneExpert(loginId) == null) {
			return false;
		} else {
			if (dtoOperation.getExpertsComponents().findOneExpert(loginId).getPassword().equals(l1.getPassword())) {

				return true;
			}

			return false;
		}

	}

	/**
	 * To save data in the expert has courses table
	 * 
	 * @param expertsHasCourses
	 */
	public void expertHasCourses(ExpertsHasCourses expertsHasCourses) {

		dtoOperation.getExpertsComponents().saveExpertsHasCourses(expertsHasCourses);

	}

	/**
	 * To get the list of study materials experts have listed
	 * 
	 * @param courseforstudymaterial
	 * @param studyMaterialId
	 * @param userName
	 *            - {@link Experts}
	 */
	public void expertsHasStudyMAterials(String courseforstudymaterial, String[] studyMaterialId, String userName) {
		int i, id;
		ArrayList<ExpertsHasStudyMaterials> ex = expertsHasStudyMAterialWithUsernameAndCouseId(courseforstudymaterial,
				userName);

		// delete all entry with the username of expert
		ex.forEach(x -> dtoOperation.getExpertsComponents().deleteExpertsHasStudyMaterials(x));

		for (i = 0; i < studyMaterialId.length; i++) {

			// getting the id from list
			id = Integer.parseInt(studyMaterialId[i]);
			// creating object
			ExpertsHasStudyMaterials expertsHasStudyMaterials = new ExpertsHasStudyMaterials(userName, id);
			// saving the object to database
			dtoOperation.getExpertsComponents().saveExpertsHasStudyMaterials(expertsHasStudyMaterials);

		}
	}

	/**
	 * To get the list of all experts
	 * 
	 * @return {@link Experts}
	 */
	public ArrayList<Experts> getAllExperts() {
		return dtoOperation.getExpertsComponents().allExperts();
	}

	/**
	 * list all the study material with a particular expert username
	 * 
	 * @param userName
	 *            - {@link Experts}
	 * @return {@link ExpertsHasStudyMaterials}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMAterialWithUsername(String userName) {
		ArrayList<ExpertsHasStudyMaterials> s1 = null;

		// To get the card type for a particular bank id
		// Native SQL Query
		String queryString = "select experts_user_name,study_materials_id_study_materials from experts_has_study_materials"
				+ " where experts_user_name =\"" + userName + "\"";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, ExpertsHasStudyMaterials.class);
		// Map result set to list of Objects
		s1 = (ArrayList<ExpertsHasStudyMaterials>) query.getResultList();

		return s1;
	}

	/**
	 * To get the expert has stuy material list accoriding to the experts username
	 * and course Id
	 * 
	 * @param id
	 *            - CourseID
	 * @param userName
	 *            - {@link Experts}
	 * @return {@link ExpertsHasStudyMaterials}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMAterialWithUsernameAndCouseId(String id,
			String userName) {
		ArrayList<ExpertsHasStudyMaterials> s1 = null;

		// To get the Stuy materials with experts name and course name
		// Native SQL Query
		String queryString = "select ehs.experts_user_name,ehs.study_materials_id_study_materials"
				+ "  from experts_has_study_materials ehs inner join courses c inner join study_materials sm "
				+ " on ehs.study_materials_id_study_materials = sm.id_study_materials"
				+ "  and sm.courses_id_course = c.id_course " + "  and ehs.experts_user_name = \"" + userName
				+ "\"  and c.course_name=\"" + id + "\"";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, ExpertsHasStudyMaterials.class);
		// Map result set to list of Objects
		s1 = (ArrayList<ExpertsHasStudyMaterials>) query.getResultList();

		return s1;
	}

	/**
	 * To get the list of experts according to the given course id
	 * 
	 * @param id
	 *            - CourseID
	 * @return {@link Experts}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Experts> findExpertsWithCoursesId(int id) {

		ArrayList<Experts> s1 = null;

		// To get the get Experts list with a particualr course id in joint tables
		// Native SQL Query
		String queryString = "select e.first_name,e.last_name,e.user_name,e.qualification,"
				+ "e.year_of_experience,e.password,e.email from experts e inner join experts_has_courses ec"
				+ " on e.user_name=ec.experts_user_name  and ec.courses_id_course=" + id;
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, Experts.class);
		// Map result set to list of Objects
		s1 = (ArrayList<Experts>) query.getResultList();

		return s1;
	}

	/**
	 * To find and expert with his username
	 * 
	 * @param userName
	 * @return {@link Experts}
	 */
	public Experts findExpertByUsername(String userName) {
		return dtoOperation.getExpertsComponents().findOneExpert(userName);
	}

	/**
	 * To find the expert with particular username
	 * 
	 * @param userName
	 *            - {@link Experts}
	 * @param email
	 *            - {@link Experts}
	 * @return loginStatus
	 */
	public boolean findOneExpert(String userName, String email) {

		if (dtoOperation.getExpertsComponents().findOneExpert(userName) == null) {
			return false;
		} else {
			// to find the experts with particualr username and email
			if (dtoOperation.getExpertsComponents().findOneExpert(userName).getEmail().equals(email)) {
				return true;
			}

			return false;
		}
	}

	/**
	 * To find the particular expert with particualr username and password and set
	 * the new password
	 * 
	 * @param userName
	 *            - {@link Experts}
	 * @param oldPassword
	 * @param newPassword
	 * @return changepassword Status
	 */
	public boolean changeTheExpertPassword(String userName, String oldPassword, String newPassword) {

		if (dtoOperation.getExpertsComponents().findOneExpert(userName) == null) {
			return false;
		} else {
			// checking that the expert exist
			if (dtoOperation.getExpertsComponents().findOneExpert(userName).getPassword().equals(oldPassword)) {
				Experts e1 = dtoOperation.getExpertsComponents().findOneExpert(userName);

				// saving the new password
				e1.setPassword(newPassword);
				dtoOperation.getExpertsComponents().saveExpert(e1);

				return true;
			}

			return false;
		}

	}

	/**
	 * To get the list of Experts student has applied for
	 * 
	 * @param userName
	 *            - {@link Students}
	 * @return {@link StudentsHasExperts}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<StudentsHasExperts> getExpertForStudent(String userName) {
		ArrayList<StudentsHasExperts> c = null;

		// To get the Experts list
		String queryString = "select * from students_has_experts where students_user_name=\"" + userName + "\"";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, StudentsHasExperts.class);
		// Map result set to list of Objects
		c = (ArrayList<StudentsHasExperts>) query.getResultList();

		return c;

	}

	/**
	 * To get the list of student following particular expert
	 * 
	 * @param userName
	 *            - {@link Experts}
	 * @return {@link StudentsHasExperts}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<StudentsHasExperts> getStudentsForExpert(String userName) {
		ArrayList<StudentsHasExperts> c = null;

		// To get the student list
		String queryString = "select * from students_has_experts where experts_user_name=\"" + userName + "\"";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, StudentsHasExperts.class);
		// Map result set to list of Objects
		c = (ArrayList<StudentsHasExperts>) query.getResultList();

		return c;

	}

}
