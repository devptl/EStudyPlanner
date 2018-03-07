package com.esp.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.Component.Components;

@Service
public class DtoOperation {

	@Autowired
	Components components;
	
	
	
	public Components getComponents() {
		return this.components;	
	}
	
	
	
	
}
