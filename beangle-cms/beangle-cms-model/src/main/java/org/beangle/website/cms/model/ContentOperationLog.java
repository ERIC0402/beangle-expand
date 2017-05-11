package org.beangle.website.cms.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import org.beangle.model.pojo.LongIdObject;
/**
 * 信息操作日志
 * @author DONHKO
 *
 */
@Entity(name="org.beangle.website.cms.model.ContentOperationLog")
public class ContentOperationLog extends LongIdObject {

	/** 操作日志 */
	private OperationLog log;
	
	/** 关联信息 */
	private Content contentId;
	
	/** 原内容 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition="CLOB",length=1048576000)
	private String content ;

	public ContentOperationLog(OperationLog log, Content contentId,
			String content) {
		this.log = log;
		this.contentId = contentId;
		this.content = content;
	}
	public ContentOperationLog() {
	}

	public OperationLog getLog() {
		return log;
	}

	public void setLog(OperationLog log) {
		this.log = log;
	}

	public Content getContentId() {
		return contentId;
	}

	public void setContentId(Content contentId) {
		this.contentId = contentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
