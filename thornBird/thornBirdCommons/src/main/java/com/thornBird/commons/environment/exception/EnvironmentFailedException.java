package com.thornBird.commons.environment.exception;

/**
 * @Description: Environment Failed Exception
 * @author: HymanHu
 * @date: 2019-01-15 16:24:51  
 */
public class EnvironmentFailedException extends RuntimeException {
	private static final long serialVersionUID = 4106529109474119036L;
	private static final String MESSAGE = "Initialize Environment failed, please check your env config file!";

	public EnvironmentFailedException() {
		super(MESSAGE);
	}

	public EnvironmentFailedException(Throwable cause) {
		super(MESSAGE, cause);
	}

}
