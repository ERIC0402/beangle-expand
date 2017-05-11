package org.beangle.wenjuan.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;

/**
 * 问卷
 * @author Donhko
 * 
 */
@Entity(name="org.beangle.wenjuan.model.WenJuan")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class WenJuan extends LongIdObject {

	/**
	 * 问卷名称
	 */
	@Size(max = 300)
	@Column(length = 300)
	private String wjmc;

	/** 主题链接 */
	@Size(max = 300)
	@Column(length = 300)
	private String ztlj;

	/** 说明 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "CLOB")
	private String sm;

	/**
	 * 问卷分类(考试问卷、评教问卷、调查问卷)
	 */
	private DictData wjfl;
	/**
	 * 问卷类型(固定问卷、评教问卷、AB卷、随机问卷)
	 */
	private DictData wjlx;

	/**
	 * 是否有效
	 */
	private boolean enabled;

	/**
	 * 合格成绩
	 */
	private Integer hgcj;

	/**
	 * 问卷总分
	 */
	private Integer wjzf;

	/**
	 * 默认选项分数（如：100,80）
	 */
	private String xxScore;

	/**
	 * 添加人
	 */
	private User tjr;

	/**
	 * 添加时间
	 */
	private Date tjsj;

	/**
	 * 添加人IP地址
	 */
	@Size(max = 64)
	@Column(length = 64)
	private String ipAddr;

	/**
	 * 问卷设置
	 */
	@OneToMany(mappedBy = "sswj", cascade = CascadeType.ALL)
	private Set<Wjsz> wjszs = CollectUtils.newHashSet();

	/**
	 * 问卷题目(仅对固定问卷、AB卷、随机问卷)
	 */
	@OneToMany(mappedBy = "sswj", cascade = CascadeType.ALL)
	private Set<Wjtm> wjtms = CollectUtils.newHashSet();

	public String getWjmc() {
		return wjmc;
	}

	public void setWjmc(String wjmc) {
		this.wjmc = wjmc;
	}

	public String getZtlj() {
		return ztlj;
	}

	public void setZtlj(String ztlj) {
		this.ztlj = ztlj;
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	public DictData getWjlx() {
		return wjlx;
	}

	public void setWjlx(DictData wjlx) {
		this.wjlx = wjlx;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getHgcj() {
		return hgcj;
	}

	public void setHgcj(Integer hgcj) {
		this.hgcj = hgcj;
	}

	public Integer getWjzf() {
		return wjzf;
	}

	public void setWjzf(Integer wjzf) {
		this.wjzf = wjzf;
	}

	public String getXxScore() {
		return xxScore;
	}

	public void setXxScore(String xxScore) {
		this.xxScore = xxScore;
	}

	public User getTjr() {
		return tjr;
	}

	public void setTjr(User tjr) {
		this.tjr = tjr;
	}

	public Date getTjsj() {
		return tjsj;
	}

	public void setTjsj(Date tjsj) {
		this.tjsj = tjsj;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Set<Wjsz> getWjszs() {
		return wjszs;
	}

	public void setWjszs(Set<Wjsz> wjszs) {
		this.wjszs = wjszs;
	}

	public Set<Wjtm> getWjtms() {
		return wjtms;
	}

	public void setWjtms(Set<Wjtm> wjtms) {
		this.wjtms = wjtms;
	}

	public DictData getWjfl() {
		return wjfl;
	}

	public void setWjfl(DictData wjfl) {
		this.wjfl = wjfl;
	}
	
}
