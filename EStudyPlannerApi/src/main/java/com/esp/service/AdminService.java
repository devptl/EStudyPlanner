package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.dto.DtoOperation;
import com.esp.model.Admin;
import com.esp.model.AdminForExperts;
import com.esp.model.AdminForStudents;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;
import com.esp.model.Students;

@Service
public class AdminService {

	@Autowired
	private DtoOperation dtoOperation;
	
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
		Admin admin = dtoOperation.getAdminComponents().findOneAdmin(loginId);

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
		ArrayList<Students> students = dtoOperation.getStudentsComponents().allStudents();

		return students;
	}

	/**
	 * To get the details statistics for experts
	 * 
	 * @return {@link AdminForExperts}
	 */
	public ArrayList<Experts> getAdminForExperts() {

		// getting the list of all the experts
		ArrayList<Experts> experts = dtoOperation.getExpertsComponents().allExperts();

		return experts;
	}

}
