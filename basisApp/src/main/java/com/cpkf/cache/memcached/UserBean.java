package com.cpkf.cache.memcached;

import java.io.Serializable;

public class UserBean implements Serializable {

	private static final long serialVersionUID = 9174194101246733501L;

	private String name;
	private int age;
	private boolean marray;

	public UserBean(String name, int age, boolean marray) {
		this.name = name;
		this.age = age;
		this.marray = marray;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isMarray() {
		return marray;
	}

	public void setMarray(boolean marray) {
		this.marray = marray;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + (marray ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBean other = (UserBean) obj;
		if (age != other.age)
			return false;
		if (marray != other.marray)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserBean [name=" + name + ", age=" + age + ", marray=" + marray + "]";
	}

}
