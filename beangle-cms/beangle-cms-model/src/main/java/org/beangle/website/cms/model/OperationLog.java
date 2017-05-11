package org.beangle.website.cms.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
/**
 * 操作日志
 * @author DONHKO
 *
 */
@Entity(name="org.beangle.website.cms.model.OperationLog")
public class OperationLog extends LongIdObject {
	
	/** 操作人 */
	private User user;
	
	/** * 菜单 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
	private String menu;
	
	/** 内容 */
	@Size(max=600)
	@javax.persistence.Column(length=600)
	private String content;
	
	/** 时间 */
	private Timestamp time;
	
	/** IP */
	@Size(max=128)
	@javax.persistence.Column(length=128)
	private String ip;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public OperationLog(User user, String menu, java.sql.Timestamp timestamp, String ip,
			String content) {
		this.user = user;
		this.menu = menu;
		this.time = timestamp;
		this.ip = ip;
		this.content = content;
	}
	public OperationLog() {
	}
	
}
