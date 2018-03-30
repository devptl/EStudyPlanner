package com.esp.service;

import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class Decoder {
	
	
	/**
	 * To decode the password 
	 * @param password
	 * @return {@link String}
	 */
	public String decodePassword(String password)
	{
		byte[] decodedString = Base64.getDecoder().decode(password);
	     String decoded = new String(decodedString);
	     return decoded;
	}

}
