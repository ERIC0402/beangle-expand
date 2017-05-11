package org.beangle.wenjuan.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;

/**
 * 考试安排
 * 
 * @author CHII
 * 
 */

//@MappedSuperclass
@Entity(name = "org.beangle.wenjuan.model.KsapParent")
@Inheritance(strategy = InheritanceType.JOINED)
public class KsapParent extends LongIdObject {

	/**
	 * 考试安排名称
	 */
	@Column(length = 90)
	private String name;

	/**
	 * 问卷
	 */
	private WenJuan wenJuan;
	
	/**
	 * 考试类别
	 */
	private DictData category;

	/**
	 * 考试类型
	 */
	private DictData type;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 学习时长（单位：分钟）
	 */
	private Integer time;

	/**
	 * 限制IP段
	 */
	@Column(length = 256)
	private String ipLimit;

	/** 考试人员 */
	@ManyToMany
	private Set<User> users = CollectUtils.newHashSet();
	/**
	 * 人工阅卷人员
	 */
	@ManyToMany
	private Set<User> rgyjrys=new HashSet<User>();
	
	public Set<User> getRgyjrys() {
		return rgyjrys;
	}

	public void setRgyjrys(Set<User> rgyjrys) {
		this.rgyjrys = rgyjrys;
	}
	/**
	 * 是否有效
	 */
	private Boolean enabled = Boolean.TRUE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WenJuan getWenJuan() {
		return wenJuan;
	}

	public void setWenJuan(WenJuan wenJuan) {
		this.wenJuan = wenJuan;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getIpLimit() {
		return ipLimit;
	}

	public void setIpLimit(String ipLimit) {
		this.ipLimit = ipLimit;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * 是否在考试时间范围内
	 * 
	 * @return
	 */
	public boolean isInTime() {
		Date now = new Date();
		if (startTime != null) {
			if (now.before(startTime)) {
				return false;
			}
		}
		if (endTime != null) {
			if (now.after(endTime)) {
				return false;
			}
		}
		return true;
	}

	public DictData getType() {
		return type;
	}

	public void setType(DictData type) {
		this.type = type;
	}

	public DictData getCategory() {
		return category;
	}

	public void setCategory(DictData category) {
		this.category = category;
	}

}
