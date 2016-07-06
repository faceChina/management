package com.jzwgj.management.exception;


import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class ShuaLianUserException extends BaseException {

	private static final long serialVersionUID = 1079245930003114452L;

	public ShuaLianUserException() {
		super();
	}

	public ShuaLianUserException(ExceptionObject exObj) {
		super(exObj);
	}

	public ShuaLianUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShuaLianUserException(String message) {
		super(message);
	}

	public ShuaLianUserException(Throwable cause) {
		super(cause);
	}

}
