package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.Component.StudentsComponents;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.model.StudentsHasExperts;

@Service
public class StudentsService {

	@Autowired
	private StudentsComponents studentsComponents;

	/**
	 * To get the student list with the username
	 * 
	 * @param userName
	 *            - Students UserName
	 * @return {@link Students}
	 */
	public Students findStudentByUsername(String userName) {
		return studentsComponents.findOneStudent(userName);
	}

	/**
	 * To get the all Student list
	 * 
	 * @return {@link Students}
	 */
	public ArrayList<Students> getAllStudents() {
		return studentsComponents.allStudents();
	}

	/**
	 * To save the student with the courses in join table
	 * 
	 * @param s
	 *            - {@link StudentsHasExperts}
	 */
	public void saveStudentsAndMinorCourse(StudentsHasCourses s) {
		studentsComponents.saveStudentsHasCourses(s);
	}

	/**
	 * To save the student that has experts in join table
	 * 
	 * @param se-
	 *            {@link StudentsHasExperts}s
	 */
	public void saveStudentsHasExperts(StudentsHasExperts se) {
		studentsComponents.saveStudentsHasExperts(se);
	}

}
