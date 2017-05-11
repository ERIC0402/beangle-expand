package org.beangle.website.cms.model;

import java.util.Date;

import javax.persistence.Entity;

import org.beangle.model.pojo.LongIdObject;
/**
 * 统计
 * @author DONHKO
 *
 */
@Entity(name="org.beangle.website.cms.model.Statistics")
public class Statistics extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9044743593314058006L;
	/** 日期 */
	private Date viewDate;
	
	/** 栏目 */
	private Column columns;
	
	/** 文章 */
	private Content content;
	
	/** 点击次数 */
	private Integer views;

	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public Column getColumns() {
		return columns;
	}

	public void setColumns(Column columns) {
		this.columns = columns;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}
}
