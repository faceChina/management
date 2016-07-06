package com.jzwgj.management.exception;


import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class ClassificationPropRealtionException extends BaseException {

	private static final long serialVersionUID = 1079245930003114452L;

	public ClassificationPropRealtionException() {
		super();
	}

	public ClassificationPropRealtionException(ExceptionObject exObj) {
		super(exObj);
	}

	public ClassificationPropRealtionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClassificationPropRealtionException(String message) {
		super(message);
	}

	public ClassificationPropRealtionException(Throwable cause) {
		super(cause);
	}

}
