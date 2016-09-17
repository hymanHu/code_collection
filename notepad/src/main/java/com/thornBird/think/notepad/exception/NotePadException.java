package com.thornBird.think.notepad.exception;

public class NotePadException extends Exception {

	private static final long serialVersionUID = 1L;

	private int code;
	private String message;
	private String description;

	public NotePadException() {
		super();
	}

	public NotePadException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotePadException(String message) {
		super(message);
	}

	public NotePadException(Throwable cause) {
		super(cause);
	}

	public NotePadException(int code, String message, String description) {
		super();
		this.code = code;
		this.message = message;
		this.description = description;
	}

	public NotePadException(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "NotePadException [code=" + code + ", message=" + message + ", description=" + description + "]";
	}

}
