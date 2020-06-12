package com.thornBird.commons.environment.bean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: Location
 * @author: HymanHu
 * @date: 2019-01-14 12:50:56
 */
public enum Location {
	dev, staging, live;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Location.class);
	
	public static final String VM_ENV = "ENV";
	public static final String ENV_DEV = "dev";
	public static final String ENV_STAGING = "staging";
	public static final String ENV_LIVE = "live";
	
	public static Location getLocation() {
		Location location = null;
		
		String locationString = System.getProperty(VM_ENV);
		LOGGER.debug("Vm environment parameter is: " + locationString);
		if (StringUtils.isNotBlank(locationString)) {
			switch (locationString) {
				case ENV_DEV:{
					location = Location.dev;
					break;
				}
				case ENV_STAGING:{
					location = Location.staging;
					break;
				}
				case ENV_LIVE:{
					location = Location.live;
					break;
				}
				default: {
					location = Location.dev;
					break;
				}
			}
		} else {
			location = Location.dev;
		}
		
		return location;
	}
}
