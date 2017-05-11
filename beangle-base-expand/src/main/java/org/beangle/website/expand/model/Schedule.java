package org.beangle.website.expand.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.system.model.DictData;

/**
 * 日程
 * 
 * @author DONHKO
 * 
 */
@Entity(name = "org.beangle.website.expand.model.Schedule")
public class Schedule extends LongIdObject {

	/**
	 */
	private static final long serialVersionUID = 5979265721672955142L;

	/** 主题 */
	@Size(max = 300)
	@Column(length = 300)
	private String title;


	/** 类型（会议、周表）*/
	private DictData type;

	/** 开始时间 */
	private Date kssj;

	/** 结束时间 */
	private Date jssj;

	/** 地点 */
	@Size(max = 300)
	@Column(length = 300)
	private String address;

	/** 详细内容 */
	@Size(max = 3000)
	@Column(length = 3000)
	private String xxnr;

	/** 是否可见 */
	private boolean visible;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DictData getType() {
		return type;
	}

	public void setType(DictData type) {
		this.type = type;
	}

	public Date getKssj() {
		return kssj;
	}

	public void setKssj(Date kssj) {
		this.kssj = kssj;
	}

	public Date getJssj() {
		return jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getXxnr() {
		return xxnr;
	}

	public void setXxnr(String xxnr) {
		this.xxnr = xxnr;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
