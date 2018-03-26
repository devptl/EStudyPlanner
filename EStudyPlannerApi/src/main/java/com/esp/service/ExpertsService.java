package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.esp.dto.DtoOperation;
import com.esp.model.Experts;
import com.esp.model.ExpertsHasCourses;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.model.LoggedUser;
import com.esp.model.RegisteredUser;
import com.esp.model.Students;
import com.esp.model.StudentsHasExperts;

@Service
public class ExpertsService {

	@Autowired
	private DtoOperation dtoOperation;

	/**
	 * For registration of particular expert in database
	 * 
	 * @param e
	 *            - {@link Experts}
	 * @return registrationStatus
	 */
	public boolean expertsRegistration(RegisteredUser registeredUser, ModelMap model) {

		String userName = registeredUser.getUserName();
		String email = registeredUser.getEmail();

		if (dtoOperation.getExpertsComponents().findOneExpert(userName) == null
				&& dtoOperation.getExpertsComponents().findByEmail(email) == null) {

			Experts e = new Experts();

			e.setFirstName(registeredUser.getFirstName());
			e.setLastName(registeredUser.getLastName());
			e.setUserName(registeredUser.getUserName());
			e.setPassword(registeredUser.getPassword());
			e.setEmail(registeredUser.getEmail());
			e.setQualification(registeredUser.getQualification());
			e.setYearOfExperience(registeredUser.getYearOfExperience());

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
	public ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMAterialWithUsername(String userName) {

		return dtoOperation.getExpertsComponents().expertsHasStudyMAterialWithUsername(userName);
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
	public ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMAterialWithUsernameAndCouseId(String id,
			String userName) {

		return dtoOperation.getExpertsComponents().expertsHasStudyMAterialWithUsernameAndCouseId(id, userName);
	}

	/**
	 * To get the list of experts according to the given course id
	 * 
	 * @param id
	 *            - CourseID
	 * @return {@link Experts}
	 */
	public ArrayList<Experts> findExpertsWithCoursesId(int id) {

		return dtoOperation.getExpertsComponents().findExpertsWithCoursesId(id);

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
	 * When an experts want to register as student
	 * 
	 * @param userName
	 */
	public boolean expertAsStudent(String userName) {
		Experts e = dtoOperation.getExpertsComponents().findOneExpert(userName);

		if (dtoOperation.getStudentsComponents().findOneStudent(userName) == null
				&& dtoOperation.getStudentsComponents().findByEmail(e.getEmail()) == null) {
			Students s = new Students();
			s.setFirstName(e.getFirstName());
			s.setLastName(e.getLastName());
			s.setUserName(e.getUserName());
			s.setGuardiansIdGuardians(1);
			s.setPassword(e.getPassword());
			s.setEmail(e.getEmail());
			dtoOperation.getStudentsComponents().saveStudent(s);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * To check if the student exist
	 * 
	 * @param userName
	 * @return
	 */
	public boolean expertAsStudentExist(String userName) {

		if (dtoOperation.getStudentsComponents().findOneStudent(userName) != null) {
			return true;
		} else {
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
	public ArrayList<StudentsHasExperts> getExpertForStudent(String userName) {

		return dtoOperation.getExpertsComponents().getExpertForStudent(userName);

	}

	/**
	 * To get the list of student following particular expert
	 * 
	 * @param userName
	 *            - {@link Experts}
	 * @return {@link StudentsHasExperts}
	 */
	public ArrayList<StudentsHasExperts> getStudentsForExpert(String userName) {

		return dtoOperation.getExpertsComponents().getStudentsForExpert(userName);

	}

}
