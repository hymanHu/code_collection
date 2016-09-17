package com.thornBird.think.notepad.common;

import java.util.HashMap;
import java.util.Map;

public enum ResultCode {
	// 特殊返回码
	UnknownError(0, "Unknown error", "Unknown error"), 
	OK(10000, "OK", " The request has succeeded."),

	// 10001-19999 系统类返回码
	RequestTimeout(10100, "Request Timeout.", "Request Timeout."),

	// 20000-29999 业务类返回码
	AccountNotFound(20000, "Account not found.", "Account not found."),
	AccountAlreadyExist(20001, "Account already exist.", "Account already exist."),
	AccountNotFoundOrPwdError(20002, "Account not found or password error.", "Account not found or password error.");
	

	private int code;
	private String message;
	private String description;
	private static Map<Integer, ResultCode> resultCodeMap = new HashMap<Integer, ResultCode>();

	static {
		for (ResultCode resultCode : ResultCode.values()) {
			resultCodeMap.put(resultCode.code, resultCode);
		}
	}

	public static ResultCode getResultCode(int code) {
		return resultCodeMap.get(code);
	}

	private ResultCode(int code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
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

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "ResultCode:[code=" + code + ",message=" + message + ",description" + description + "]";
	}

	public static void main(String[] args) {
		System.out.println(ResultCode.getResultCode(0).toString());
	}

}
