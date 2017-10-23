package it.uniroma3.icr.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.icr.model.Administrator;

public class AdminValidatorInutile implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Administrator.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "username", "Non deve essere vuoto");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password", "Non deve essere vuoto");

	}

}
