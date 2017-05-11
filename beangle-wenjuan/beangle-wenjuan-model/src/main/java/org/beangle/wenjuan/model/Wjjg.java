package org.beangle.wenjuan.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;

/**
 * 问卷结果
 * @author Donhko
 *
 */
@Entity(name="org.beangle.wenjuan.model.Wjjg")
public class Wjjg extends LongIdObject{

	/**
	 * 用户
	 */
	private User user;

	/**
	 * 问卷
	 */
	private WenJuan wj;

	/**
	 * 分数
	 */
	private Float score;

	/**
	 * 试卷总分
	 */
	private Integer totalScore;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 已用时长
	 */
	private int yysc;

	/**
	 * 提交人IP地址
	 */
	@Size(max=64)
	@Column(length=64)
	private String ipAddr;
	
	/**
	 * 唯一标识
	 */
	@Size(max=64)
	@Column(length=64)
	private String wybs;
	
	/**
	 * 问卷选项
	 */
	@OneToMany(mappedBy = "wjjg", cascade = CascadeType.ALL)
	private Set<Wjjgtm> wjjgtms = CollectUtils.newHashSet();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WenJuan getWj() {
		return wj;
	}

	public void setWj(WenJuan wj) {
		this.wj = wj;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}



	public int getYysc() {
		return yysc;
	}

	public void setYysc(int yysc) {
		this.yysc = yysc;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getWybs() {
		return wybs;
	}

	public void setWybs(String wybs) {
		this.wybs = wybs;
	}

	public Set<Wjjgtm> getWjjgtms() {
		return wjjgtms;
	}

	public void setWjjgtms(Set<Wjjgtm> wjjgtms) {
		this.wjjgtms = wjjgtms;
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

}
