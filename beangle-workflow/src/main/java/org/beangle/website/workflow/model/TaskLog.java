package org.beangle.website.workflow.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;

/**
 * 任务操作日志
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.workflow.model.TaskLog")
public class TaskLog extends LongIdObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5521929971248063263L;
	/**
	 * 操作者
	 */
	private User user;
	/**
	 * 任务
	 */
	private TaskNode taskNode;
	/**
	 * 操作时间
	 */
	private Timestamp time;
	/**
	 * 备注
	 */
	@Size(max=1000)
	@Column(length=1000)
	private String remark;
	
	/**
	 * 访问端IP地址
	 */
	@Size(max=128)
	@Column(length=128)
	private String ipAddress;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public TaskNode getTaskNode() {
		return taskNode;
	}

	public void setTaskNode(TaskNode taskNode) {
		this.taskNode = taskNode;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
