package com.esp.service;

import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class Decoder {
	
	public String decodePassword(String password)
	{
		byte[] decodedString = Base64.getDecoder().decode(password);
	     String decoded = new String(decodedString);
	     return decoded;
	}

}
