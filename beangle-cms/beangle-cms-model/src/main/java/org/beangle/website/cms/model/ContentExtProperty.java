package org.beangle.website.cms.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.system.model.DictData;
/**
 * 扩展属性信息
 * @author DONHKO
 *
 */
@Entity(name="org.beangle.website.cms.model.ContentExtProperty")
public class ContentExtProperty extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1378874203955823424L;

	/** 关联信息 */
	private Content content;

	/** 载体类型 */
	private DictData byType;
	
	/** 记录形式 */
	private DictData recordType;
	
	/** 公开类别 */
	private DictData publicType;
	
	/** 学校 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
	private String school;
	
	/** 文号 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
	private String code;
	
	/** 存放路径 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
	private String savePath;

	/** 索引 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
	private String indent;
	
	/** 生成日期 */
	private Date produceDate;
	
	/** 部门 */
	private DictData department;

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public DictData getByType() {
		return byType;
	}

	public void setByType(DictData byType) {
		this.byType = byType;
	}

	public DictData getRecordType() {
		return recordType;
	}

	public void setRecordType(DictData recordType) {
		this.recordType = recordType;
	}

	public DictData getPublicType() {
		return publicType;
	}

	public void setPublicType(DictData publicType) {
		this.publicType = publicType;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getIndent() {
		return indent;
	}

	public void setIndent(String indent) {
		this.indent = indent;
	}

	public Date getProduceDate() {
		return produceDate;
	}

	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}

	public DictData getDepartment() {
		return department;
	}

	public void setDepartment(DictData department) {
		this.department = department;
	}
	
}
