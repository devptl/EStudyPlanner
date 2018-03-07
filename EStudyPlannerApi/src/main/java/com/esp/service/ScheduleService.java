package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.esp.dto.DtoOperation;
import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.Schedule;
import com.esp.model.Students;

@Service
public class ScheduleService {
	
	@Autowired
	private DtoOperation dtoOperation;
	
	@Autowired
	private CoursesService coursesService;

	@Autowired
	private Initialiser initialiser;

	

	public void saveSchedule(Schedule s,ModelMap model) {
	
		dtoOperation.getComponents().saveSchedule(s);
		Students s1=dtoOperation.getComponents().findOneStudent(s.getStudentsUserName());
		
		ArrayList<Courses> mainCourses = coursesService.mainCoursesById(s1.getField());

		ArrayList<Experts> allExperts = dtoOperation.getComponents().allExperts();

		initialiser.schedulerInitialiser(mainCourses, allExperts,s.getStudentsUserName(),model);
		
	}
	
	public Schedule findSchedule(String username) {
		return dtoOperation.getComponents().findOneSchedule(username);
	}
	
}
