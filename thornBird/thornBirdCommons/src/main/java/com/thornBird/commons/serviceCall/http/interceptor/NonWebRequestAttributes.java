package com.thornBird.commons.serviceCall.http.interceptor;

import org.springframework.web.context.request.RequestAttributes;

/**
 * @Description: Non Web Request Attributes
 * @author: HymanHu
 * @date: 2019-04-19 14:36:19
 */
public class NonWebRequestAttributes implements RequestAttributes {

	@Override
	public Object getAttribute(String name, int scope) {
		return null;
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {

	}

	@Override
	public void removeAttribute(String name, int scope) {

	}

	@Override
	public String[] getAttributeNames(int scope) {
		return null;
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback, int scope) {

	}

	@Override
	public Object resolveReference(String key) {
		return null;
	}

	@Override
	public String getSessionId() {
		return null;
	}

	@Override
	public Object getSessionMutex() {
		return null;
	}

}
