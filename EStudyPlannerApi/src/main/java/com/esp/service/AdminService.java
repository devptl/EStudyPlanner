package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.Component.AdminComponents;
import com.esp.Component.ExpertsComponents;
import com.esp.Component.StudentsComponents;
import com.esp.model.Admin;
import com.esp.model.AdminForExperts;
import com.esp.model.AdminForStudents;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.Students;

@Service
public class AdminService {

	@Autowired
	private AdminComponents adminComponents;
	
	@Autowired
	private StudentsComponents studentsComponents;
	
	@Autowired
	private ExpertsComponents expertsComponents; 
	
	@Autowired
	private Encoder encoder;

	/**
	 * For login as an admin
	 * 
	 * @param l1
	 *            - {@link LoggedUser}
	 * @return LoginStatus
	 */
	public boolean adminLogin(LoggedUser l1) {
		String loginId = l1.getUserName();
		Admin admin = adminComponents.findOneAdmin(loginId);

		// checking if expert exist with username and password
		if (admin == null) {
			return false;
		} else {
			String encodedPassword = encoder.encodePassword(l1.getPassword());
			
			if (admin.getPassword().equals(encodedPassword)) {
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
	public ArrayList<Students> getAdminForStudent() {

		// getting list of all the students
		ArrayList<Students> students = studentsComponents.allStudents();

		return students;
	}

	/**
	 * To get the details statistics for experts
	 * 
	 * @return {@link AdminForExperts}
	 */
	public ArrayList<Experts> getAdminForExperts() {

		// getting the list of all the experts
		ArrayList<Experts> experts = expertsComponents.allExperts();

		return experts;
	}

}
