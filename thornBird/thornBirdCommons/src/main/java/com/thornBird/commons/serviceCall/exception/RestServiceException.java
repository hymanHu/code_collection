package com.thornBird.commons.serviceCall.exception;

import com.thornBird.commons.serviceCall.http.HttpStatus;

/**
 * @Description: Rest Service Exception
 * @author: HymanHu
 * @date: 2019-01-29 21:53:50
 */
public class RestServiceException extends RuntimeException {
	private static final long serialVersionUID = 6056932528407692580L;
	private int status;
	private String reason;
    private TYPE type;

	public RestServiceException(String reason) {
		super();
		this.reason = reason;
		setType();
	}

	public RestServiceException(int status, String reason) {
		super(reason);
		this.reason = reason;
		this.status = status;
		setType();
	}
	
	public RestServiceException(String reason, Throwable cause) {
		super(reason, cause);
		this.reason = reason;
		setType();
	}

	public RestServiceException(int status, String reason, Throwable cause) {
		super(reason, cause);
		this.status = status;
		this.reason = reason;
		setType();
	}

	public RestServiceException(int status, String reason, TYPE type) {
		super(reason);
		this.status = status;
		this.reason = reason;
		this.type = type;
		setType();
	}
	
	public RestServiceException(int status, String reason, TYPE type, Throwable cause) {
		super(reason, cause);
		this.status = status;
		this.reason = reason;
		this.type = type;
		setType();
	}

	private void setType() {
    	if (getStatus() == 0) {
    		type = TYPE.UNKNOWN;
    	} else if (HttpStatus.getHttpStatus(getStatus()).is4xxClientError()) {
    		type = TYPE.CLIENT_ERROR;
    	} else if (HttpStatus.getHttpStatus(getStatus()).is5xxServerError()) {
    		type = TYPE.SERVER_ERROR;
    	} else if (getStatus() == HttpStatus.NO_CONTENT.getCode()) {
    		type = TYPE.NO_CONTENT;
    	} else {
    		type = TYPE.UNKNOWN;
    	}
    }
    
	public String getReason() {
		return reason;
	}

	public int getStatus() {
		return status;
	}
	
	public TYPE getType() {
		return type;
	}

	public static enum TYPE {
		CLIENT_ERROR, //call had a 400 error
        SERVER_ERROR, //call had a 500 error
        ADAPTER_ERROR, //there was some kind of IO/Encoding/Adapting issue
        NO_CONTENT, //call came back with a 204 no content response
        UNKNOWN; //default error
	}
}
