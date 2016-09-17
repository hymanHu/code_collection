package com.cpkf.testBean;

import java.util.Date;

public class Student {
	private long id;
	private long studentNum;
	private String name;
	private String gender;
	private int age;
	private Date birthday;

	public Student() {
	}

	public Student(long id, long studentNum, String name, String gender, int age, Date birthday) {
		this.id = id;
		this.studentNum = studentNum;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.birthday = birthday;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(long studentNum) {
		this.studentNum = studentNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
