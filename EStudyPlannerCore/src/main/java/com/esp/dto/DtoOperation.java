package com.esp.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.Component.AdminComponents;
import com.esp.Component.CoursesComponents;
import com.esp.Component.ExpertsComponents;
import com.esp.Component.ScheduleComponents;
import com.esp.Component.StudentsComponents;
import com.esp.Component.StudyMaterialsComponents;

@Service
public class DtoOperation {

	@Autowired
	private CoursesComponents coursesComponents;
	
	@Autowired
	private StudentsComponents studentsComponents;
	
	@Autowired
	private ExpertsComponents expertsComponents;
	
	@Autowired
	private StudyMaterialsComponents studyMaterialsComponents;
	
	@Autowired
	private ScheduleComponents scheduleComponents;
	
	@Autowired
	private AdminComponents adminComponents; 

	public CoursesComponents getCoursesComponents() {
		return coursesComponents;
	}

	public StudentsComponents getStudentsComponents() {
		return studentsComponents;
	}

	public ExpertsComponents getExpertsComponents() {
		return expertsComponents;
	}

	public StudyMaterialsComponents getStudyMaterialsComponents() {
		return studyMaterialsComponents;
	}

	public ScheduleComponents getScheduleComponents() {
		return scheduleComponents;
	}

	public AdminComponents getAdminComponents() {
		return adminComponents;
	}	
	
}
