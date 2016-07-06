package com.jzwgj.management.exception;


import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class SlcoinActivityException extends BaseException {

	private static final long serialVersionUID = 1079245930003114452L;

	public SlcoinActivityException() {
		super();
	}

	public SlcoinActivityException(ExceptionObject exObj) {
		super(exObj);
	}

	public SlcoinActivityException(String message, Throwable cause) {
		super(message, cause);
	}

	public SlcoinActivityException(String message) {
		super(message);
	}

	public SlcoinActivityException(Throwable cause) {
		super(cause);
	}

}
