package org.beangle.wenjuan.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;

//@MappedSuperclass
@Entity(name = "org.beangle.wenjuan.model.ZxksParent")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ZxksParent extends LongIdObject {
	/**
	 * 学员
	 */
	private User user;
	/**
	 * 问卷结果
	 */
	private Wjjg wjjg;

	/**
	 * 考试安排
	 */
	private KsapParent ksap;

	private Long ksapid;

	/**
	 * 完成答卷
	 * 
	 * @return
	 */
	private Boolean finished = Boolean.FALSE;

	public Long getKsapid() {
		return ksapid;
	}

	public void setKsapid(Long ksapid) {
		this.ksapid = ksapid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Wjjg getWjjg() {
		return wjjg;
	}

	public void setWjjg(Wjjg wjjg) {
		this.wjjg = wjjg;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public KsapParent getKsap() {
		return ksap;
	}

	public void setKsap(KsapParent ksap) {
		this.ksap = ksap;
	}

	/**
	 * 是否时间到
	 * 
	 * @return
	 */
	public boolean isTimeUp() {
		if (getKsap() != null) {
			if (!getKsap().isInTime()) {
				return true;
			}
			// 有问卷结果、并且有开始时间，有学习时长，并大于0
			if (wjjg != null && wjjg.getStartTime() != null && getKsap().getTime() != null && getKsap().getTime() > 0) {
				Date endTime = new Date(wjjg.getEndTime().getTime() + getKsap().getTime() * 60000);
				if (endTime.getTime() < System.currentTimeMillis()) {
					return true;
				}
			}
		}
		return false;
	}

}
