package org.beangle.wenjuan.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictTree;

/**
 * 题目超类
 * @author Donhko
 *
 */
@MappedSuperclass
public class TmObject extends LongIdTimeObject{
	
	/**
	 * 题目名称
	 */
	@Size(max=300)
	@Column(length=300)
	private String tmmc;

	/**
	 * 题目排序
	 */
	private int px;

	/**
	 * 题目内容
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "CLOB")
	private String tmnr;
	
	/**
	 * 题目分类(多级)
	 */
	private DictTree tmfl;
	
	/**
	 * 题目类型（单选、多选、判断、节点、网格、填空、排序、简答）
	 */
	private DictData tmlx;

	/**
	 * 选项模板
	 */
	private Xxmb xxmb;
	
	/** 
	 * 选项排列方式（横排、竖排）
	 */
	private DictData xxplfs;
	
	/**
	 * 选项显示顺序是否固定
	 */
	private Boolean xxsfgd ;

	/**
	 * 正确答案
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "CLOB")
	private String zqda;

	/** 图片*/
	@Size(max=300)
	@Column(length=300)
	private String pic;
	
	/**
	 * 添加人
	 */
	private User tjr;

	/**
	 * 是否有效
	 */
	private boolean enabled = true;
	
	/**
	 * 题目难度：简单、一般、困难
	 */
	private DictData tmnd;
	/**
	 * 适用范围：练习、考试
	 */
	private DictData syfw;

	public TmObject() {
		super();
	}

	public TmObject(Long id) {
		super(id);
	}

	public String getTmmc() {
		return tmmc;
	}

	public void setTmmc(String tmmc) {
		this.tmmc = tmmc;
	}

	public int getPx() {
		return px;
	}

	public void setPx(int px) {
		this.px = px;
	}

	public String getTmnr() {
		return tmnr;
	}

	public void setTmnr(String tmnr) {
		this.tmnr = tmnr;
	}

	public DictTree getTmfl() {
		return tmfl;
	}

	public void setTmfl(DictTree tmfl) {
		this.tmfl = tmfl;
	}

	public DictData getTmlx() {
		return tmlx;
	}

	public void setTmlx(DictData tmlx) {
		this.tmlx = tmlx;
	}

	public Xxmb getXxmb() {
		return xxmb;
	}

	public void setXxmb(Xxmb xxmb) {
		this.xxmb = xxmb;
	}

	public DictData getXxplfs() {
		return xxplfs;
	}

	public void setXxplfs(DictData xxplfs) {
		this.xxplfs = xxplfs;
	}

	public String getZqda() {
		return zqda;
	}

	public void setZqda(String zqda) {
		this.zqda = zqda;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getXxsfgd() {
		return xxsfgd;
	}

	public void setXxsfgd(Boolean xxsfgd) {
		this.xxsfgd = xxsfgd;
	}

	public DictData getTmnd() {
		return tmnd;
	}

	public void setTmnd(DictData tmnd) {
		this.tmnd = tmnd;
	}

	public DictData getSyfw() {
		return syfw;
	}

	public void setSyfw(DictData syfw) {
		this.syfw = syfw;
	}

	public User getTjr() {
		return tjr;
	}

	public void setTjr(User tjr) {
		this.tjr = tjr;
	}
	
}
