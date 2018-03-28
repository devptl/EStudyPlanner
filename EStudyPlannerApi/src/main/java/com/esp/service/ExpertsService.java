package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.esp.dto.DtoOperation;
import com.esp.model.Experts;
import com.esp.model.ExpertsHasCourses;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.model.RegisteredUser;
import com.esp.model.Students;
import com.esp.model.StudentsHasExperts;
import com.esp.model.Users;

@Service
public class ExpertsService {

	@Autowired
	private DtoOperation dtoOperation;
	
	@Autowired
	private Encoder encoder;

	/**
	 * For registration of particular expert in database
	 * 
	 * @param registeredUser
	 * @param model
	 * @return registrationStatus
	 */
	public boolean expertsRegistration(RegisteredUser registeredUser, ModelMap model) {

		String userName = registeredUser.getUserName();
		String email = registeredUser.getEmail();

		if (dtoOperation.getUserComponent().findOne(userName) == null
				&& dtoOperation.getUserComponent().findByEmail(email) == null) {

			Users user = new Users();
			Experts experts = new Experts();

			user.setUserName(userName);
			experts.setUserName(userName);
			
			user.setFirstName(registeredUser.getFirstName());
			user.setLastName(registeredUser.getLastName());
			
			//encode the password
            String encodedPassword = encoder.encodePassword(registeredUser.getPassword());
			
			user.setPassword(encodedPassword);
			user.setEmail(registeredUser.getEmail());
			experts.setQualification(registeredUser.getQualification());
			experts.setYearOfExperience(registeredUser.getYearOfExperience());
			

			dtoOperation.getUserComponent().saveUser(user);
			dtoOperation.getExpertsComponents().saveExpert(experts);

			return true;
		} else
			return false;
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
	 * When an experts want to register as student
	 * 
	 * @param userName
	 */
	public boolean expertAsStudent(String userName) {
		
		if ( dtoOperation.getStudentsComponents().findOneStudent(userName) == null) {
			
			//setting the data for experts as student
			Students student = new Students();
			
			student.setGuardiansIdGuardians(1);
			
			//saving the new student
			dtoOperation.getStudentsComponents().saveStudent(student);
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
