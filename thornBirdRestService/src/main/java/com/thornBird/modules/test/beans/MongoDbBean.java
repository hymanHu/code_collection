package com.thornBird.modules.test.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Description: MongoDb Bean
 * @author: HymanHu
 * @date: 2019-08-19 15:53:10
 */
@Document(collection = "test")
public class MongoDbBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Field("_id")
    private String _id;
	@Field("user_id")
	private int userId;
	@Field("user_name")
	private String userName;
	@Field("create_date")
	private Date createDate;
	@Field("my_list")
	private List<String> myList;
	@Field("my_map")
	private Map<String, String> myMap;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<String> getMyList() {
		return myList;
	}

	public void setMyList(List<String> myList) {
		this.myList = myList;
	}

	public Map<String, String> getMyMap() {
		return myMap;
	}

	public void setMyMap(Map<String, String> myMap) {
		this.myMap = myMap;
	}

}
