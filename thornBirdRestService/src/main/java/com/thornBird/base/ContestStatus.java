package com.thornBird.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: enum test
 * @author: HymanHu
 * @date: 2019-01-15 13:50:32  
 */
public enum ContestStatus {
	Inactive(-1, "Inactive", " AND ((m_Contests.StartDate IS NULL) OR ((m_Contests.EndDate IS NOT NULL) AND (m_Contests.EndDate < ^)))"), 
	Live(0, "Live", " AND (m_Contests.StartDate IS NOT NULL) AND (m_Contests.StartDate <= ^) AND ((m_Contests.EndDate IS NULL) OR (m_Contests.EndDate >= ^))"), 
	Future(1, "Future", " AND (m_Contests.StartDate IS NOT NULL) AND (m_Contests.StartDate > ^)");

	private ContestStatus(int code, String message, String queryString) {
		this.code = code;
		this.message = message;
		this.queryString = queryString;
	}

	private int code;
	private String message;
	private String queryString;

	private static Map<String, ContestStatus> ContestStatusMap = new HashMap<String, ContestStatus>();

	static {
		for (ContestStatus contestStatus : ContestStatus.values()) {
			ContestStatusMap.put(contestStatus.getMessage().toLowerCase(), contestStatus);
		}
	}
	
	public static ContestStatus getContestStatus(String status) {
		return status== null || ContestStatusMap.get(status.toLowerCase()) == null ? 
				ContestStatus.Live : ContestStatusMap.get(status.toLowerCase());
	}
	
	public String getQueryString() {
		return queryString;
	}
	
	public void setQueryString(String queryString) {
		this.queryString = queryString;
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
	
}
