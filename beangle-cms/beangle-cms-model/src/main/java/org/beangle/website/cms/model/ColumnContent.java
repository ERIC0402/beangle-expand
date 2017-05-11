package org.beangle.website.cms.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.system.model.DictData;
import org.beangle.website.workflow.model.Task;
/**
 * 栏目-信息
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.cms.model.ColumnContent")
public class ColumnContent extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8983121976948197367L;

	/** 关联栏目 */
	@NotNull
	private Column column;
	
	/** 关联信息 */
	@NotNull
	private Content content;
	
	/** 发布日期 */
	private Date publishDate;
	
	/** 结束日期 */
	private Date endDate;
	
	/** 是否置顶 */
	private boolean doesTop = false;
	
	/** 置顶开始日期 */
	private Date topStartDate;
	
	/** 置顶结束日期 */
	private Date topEndDate;
	
	/** 是否主栏目 */
	private boolean doesMainColumn = false;
	
	/** 信息属性（推荐到首页、推荐到栏目、热点、重要） */
	@ManyToMany
	@JoinTable(name="CMS_CC_PROPERTIES")
	private Set<DictData> contentProperties = CollectUtils.newHashSet();
	
	/** 信息状态（草稿、待审核、待发布、已发布、退回、删除（假删除）、不通过（任务结束）） */
	private DictData contentState;
	
	/** 阅读权限（校内、所有人） */
	private DictData readPurview;
	
	/** 关联流程 */
	private Task task;

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isDoesTop() {
		return doesTop;
	}

	public void setDoesTop(boolean doesTop) {
		this.doesTop = doesTop;
	}

	public Date getTopStartDate() {
		return topStartDate;
	}

	public void setTopStartDate(Date topStartDate) {
		this.topStartDate = topStartDate;
	}

	public Date getTopEndDate() {
		return topEndDate;
	}

	public void setTopEndDate(Date topEndDate) {
		this.topEndDate = topEndDate;
	}

	public boolean isDoesMainColumn() {
		return doesMainColumn;
	}

	public void setDoesMainColumn(boolean doesMainColumn) {
		this.doesMainColumn = doesMainColumn;
	}

	public Set<DictData> getContentProperties() {
		return contentProperties;
	}

	public void setContentProperties(Set<DictData> contentProperties) {
		this.contentProperties = contentProperties;
	}

	public DictData getContentState() {
		return contentState;
	}

	public void setContentState(DictData contentState) {
		this.contentState = contentState;
	}

	public DictData getReadPurview() {
		return readPurview;
	}

	public void setReadPurview(DictData readPurview) {
		this.readPurview = readPurview;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
