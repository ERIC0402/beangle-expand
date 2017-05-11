package org.beangle.wenjuan.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictTree;
/**
 * 问卷设置
 * @author Donhko
 *
 */
@Entity(name="org.beangle.wenjuan.model.Wjsz")
public class Wjsz extends LongIdObject{
	
	/**
	 * 所属问卷
	 */
	private WenJuan sswj;
	
	/**
	 * 题库
	 */
	private TiKu tk;

	/**
	 * 题目分类(多级)
	 */
	private DictTree tmfl;

	/**
	 * 题目类型（单选、多选、判断、节点、网格、填空、排序、简答）
	 */
	private DictData tmlx;
	
	/**
	 * 抽取题目数量
	 */
	private Integer tmsl;
	
	/**
	 * 每题分数
	 */
	private Float mtfs;
	/**
	 * 题目难度：简单、一般、困难
	 */
	private DictData tmnd;
	/**
	 * 随机抽取的题目
	 */
	@Transient
	private List<TiMu> tiMus;

	public WenJuan getSswj() {
		return sswj;
	}

	public void setSswj(WenJuan sswj) {
		this.sswj = sswj;
	}

	public TiKu getTk() {
		return tk;
	}

	public void setTk(TiKu tk) {
		this.tk = tk;
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

	public Integer getTmsl() {
		return tmsl;
	}

	public void setTmsl(Integer tmsl) {
		this.tmsl = tmsl;
	}

	public Float getMtfs() {
		return mtfs;
	}

	public void setMtfs(Float mtfs) {
		this.mtfs = mtfs;
	}

	public List<TiMu> getTiMus() {
		return tiMus;
	}

	public void setTiMus(List<TiMu> tiMus) {
		this.tiMus = tiMus;
	}

	public DictData getTmnd() {
		return tmnd;
	}

	public void setTmnd(DictData tmnd) {
		this.tmnd = tmnd;
	}
	
}
