package org.beangle.wenjuan.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;
/**
 * 选项模板
 * @author Donhko
 *
 */
@Entity(name="org.beangle.wenjuan.model.Xxmb")
public class Xxmb extends LongIdObject {

	/** 
	 * 模板类型 
	 */
	private DictData mblx;
	
	/**
	 * 模板名称
	 */
	@Size(max=300)
	@Column(length=300)
	private String mbmc;
	
	/**
	 * 排序(标识：A/B/C/D)
	 */
	private String px;
	
	/** 
	 * 选项内容
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "CLOB")
	private String xxnr;
	
	/** 
	 * 是否有效 
	 */
	private boolean enabled = true;

	public DictData getMblx() {
		return mblx;
	}

	public void setMblx(DictData mblx) {
		this.mblx = mblx;
	}

	public String getPx() {
		return px;
	}

	public void setPx(String px) {
		this.px = px;
	}

	public String getXxnr() {
		return xxnr;
	}

	public void setXxnr(String xxnr) {
		this.xxnr = xxnr;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getMbmc() {
		return mbmc;
	}

	public void setMbmc(String mbmc) {
		this.mbmc = mbmc;
	}

}