package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.dto.DtoOperation;
import com.esp.model.Experts;
import com.esp.model.LoggedUser;

@Service
public class ExpertsService {

	@Autowired
	private DtoOperation dtoOperation;

	/**
	 * For registration of particular expert in database
	 * 
	 * @param e
	 * @return registrationStatus
	 */
	public boolean expertsRegistration(Experts e) {
		String id = e.getUserName();
		if (dtoOperation.getComponents().findOneExpert(id) == null) {
			dtoOperation.getComponents().saveExpert(e);
			return true;
		} else
			return false;
	}

	/**
	 * When a particular experts logs in with a specific username
	 * 
	 * @param l1
	 * @return loginStatus
	 */
	public boolean expertsLogin(LoggedUser l1) {

		String loginId = l1.getUserName();
		if (dtoOperation.getComponents().findOneExpert(loginId) == null) {
			return false;
		} else {
			if (dtoOperation.getComponents().findOneExpert(loginId).getPassword().equals(l1.getPassword())) {
				return true;
			}

			return false;
		}

	}

	/**
	 * To get the list of all experts
	 * 
	 * @return {@link Experts}
	 */
	public ArrayList<Experts> getAllExperts() {
		return dtoOperation.getComponents().allExperts();
	}

}
