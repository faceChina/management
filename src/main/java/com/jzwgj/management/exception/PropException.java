package com.jzwgj.management.exception;


import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class PropException extends BaseException {

	private static final long serialVersionUID = 1079245930003114452L;

	public PropException() {
		super();
	}

	public PropException(ExceptionObject exObj) {
		super(exObj);
	}

	public PropException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropException(String message) {
		super(message);
	}

	public PropException(Throwable cause) {
		super(cause);
	}

}
