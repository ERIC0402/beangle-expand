package org.beangle.wenjuan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

/**
 * 题目选项
 * @author Donhko
 *
 */
@Entity(name="org.beangle.wenjuan.model.Tmxx")
public class Tmxx extends LongIdObject implements Comparable<Tmxx>{
	
	/**
	 * 所属题目
	 */
	private TiMu sstm;
	
	/**
	 * 排序(例如：A/B/C/D)
	 */
	@Size(max=2)
	@Column(length=2)
	private String px;
	
	/**
	 * 选项内容
	 */
	@Size(max=300)
	@Column(length=300)
	private String xxnr;

	/**
	 * 选项分数
	 */
	@Size(max=4)
	@Column(length=4)
	private String xxfs;
	
	public String getXxfs() {
		return xxfs;
	}

	public void setXxfs(String xxfs) {
		this.xxfs = xxfs;
	}

	public Tmxx() {
		super();
	}

	public Tmxx(Long id) {
		super(id);
	}

	public TiMu getSstm() {
		return sstm;
	}

	public void setSstm(TiMu sstm) {
		this.sstm = sstm;
	}

	public String getPx() {
		if(px == null){
			px = "";
		}
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

	public int compareTo(Tmxx o) {
		if(this.px == null){
			return 0;
		}
		return this.px.compareTo(o.getPx());
	}
}
