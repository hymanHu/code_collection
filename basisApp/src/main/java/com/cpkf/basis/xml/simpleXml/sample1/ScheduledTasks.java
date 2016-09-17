package com.cpkf.basis.xml.simpleXml.sample1;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.Persister;

/**
 * 简单xmlBo对象--对应"/testFile/task.xml"
 * 只读取xml文件其中一个节点 & 读取所有
 * 1、类名上指定为element；
 * 2、属性上指定path & text，如果为attribute要声明；
 * 3、空构造；
 * 4、调用read方法时候声明在非严格模式下阅读，参数为false
 * 5、里面包含多层节点的时候，读取root时，还应继续构造里层对象
 * @author Administrator
 */
@Element(name = "scheduledTasks")
public class ScheduledTasks {

	@Text
	@Path("scheduledTasks/deleteRemoved/delay")
	private int deleteRemoved;
	@Text
	@Path("scheduledTasks/deleteArchived/delay")
	private int deleteArchived;

	public ScheduledTasks() {
	}

	public ScheduledTasks(int deleteRemoved, int deleteArchived) {
		this.deleteRemoved = deleteRemoved;
		this.deleteArchived = deleteArchived;
	}

	public int getDeleteRemoved() {
		return deleteRemoved;
	}

	public void setDeleteRemoved(int deleteRemoved) {
		this.deleteRemoved = deleteRemoved;
	}

	public int getDeleteArchived() {
		return deleteArchived;
	}

	public void setDeleteArchived(int deleteArchived) {
		this.deleteArchived = deleteArchived;
	}

	@Override
	public String toString() {
		return "ScheduledTasks [deleteRemoved=" + deleteRemoved
				+ ", deleteArchived=" + deleteArchived + "]";
	}
	
	public static void main(String[] args) throws Exception {
		Serializer serializer = new Persister();
		ScheduledTasks scheduledTasks = serializer.read(ScheduledTasks.class, 
				ScheduledTasks.class.getClassLoader().getResourceAsStream("testFile/task.xml"), false);
		System.out.println(scheduledTasks.toString());
	}
}
