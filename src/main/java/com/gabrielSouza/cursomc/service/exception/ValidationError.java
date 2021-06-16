package com.gabrielSouza.cursomc.service.exception;

import java.util.ArrayList;
import java.util.List;

import com.gabrielSouza.cursomc.resource.exception.StandardError;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;

	private List<FieldException> Errors = new ArrayList<>();

	public ValidationError(Integer status, String message, Long timeStamp) {

		super(status, message, timeStamp);
	}

	public List<FieldException> getError() {
		return Errors;
	}

	public void addError(String fieldName, String Message) {

		Errors.add(new FieldException(fieldName, Message));
	}

}
