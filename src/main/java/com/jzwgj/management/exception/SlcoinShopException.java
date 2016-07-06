package com.jzwgj.management.exception;


import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.util.exception.enums.ExceptionObject;

public class SlcoinShopException extends BaseException {

	private static final long serialVersionUID = 1079245930003114452L;

	public SlcoinShopException() {
		super();
	}

	public SlcoinShopException(ExceptionObject exObj) {
		super(exObj);
	}

	public SlcoinShopException(String message, Throwable cause) {
		super(message, cause);
	}

	public SlcoinShopException(String message) {
		super(message);
	}

	public SlcoinShopException(Throwable cause) {
		super(cause);
	}

}
