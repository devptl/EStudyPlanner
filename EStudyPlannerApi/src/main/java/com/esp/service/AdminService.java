package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.Component.ExpertsComponents;
import com.esp.Component.StudentsComponents;
import com.esp.model.Experts;
import com.esp.model.Students;

@Service
public class AdminService {

	@Autowired
	private StudentsComponents studentsComponents;

	@Autowired
	private ExpertsComponents expertsComponents;

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
