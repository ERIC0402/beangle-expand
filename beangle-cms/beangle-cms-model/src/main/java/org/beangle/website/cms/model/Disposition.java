package org.beangle.website.cms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
/**
 * 处理意见
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.cms.model.Disposition")
public class Disposition extends LongIdObject {

	/**
	 * 留言
	 */
	private OnlineMess onlineMess;
	
	/**
	 * 处理人
	 */
	private User user;
	
	/**
	 * 处理时间
	 */
	private Date time;
	
	/**
	 * 处理人IP
	 */
	@Size(max=128)
	@Column(length=128)
	private String ipAddr;
	
	/**
	 * 处理意见
	 */
	@Size(max=3000)
	@Column(length=3000)
	private String views;
	
	/**
	 * 状态(0:未处理；1:已处理)
	 */
	private boolean state = false;

	public OnlineMess getOnlineMess() {
		return onlineMess;
	}

	public void setOnlineMess(OnlineMess onlineMess) {
		this.onlineMess = onlineMess;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
