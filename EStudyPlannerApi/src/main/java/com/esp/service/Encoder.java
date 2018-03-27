package com.esp.service;

import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class Encoder {
	
	public String encodePassword(String password)
	{
		 String encodedString =  Base64.getEncoder().encodeToString(password.getBytes());
		 return encodedString;
	}

}
