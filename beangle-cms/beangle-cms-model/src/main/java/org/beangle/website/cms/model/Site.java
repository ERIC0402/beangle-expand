package org.beangle.website.cms.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.Group;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.workflow.model.Workflow;

/**
 * 站点
 * @author DONHKO
 * 
 */
@Entity(name="org.beangle.website.cms.model.Site")
public class Site extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5163956861318527443L;

	/** 代码 */
	@Size(max=32)
	@javax.persistence.Column(length=32)
	private String code;

	/** 名称 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
	private String name;
	
	/** 域名 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
	private String domain;
	
	/** 存放路径 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
	private String savePath;
	
	/** 是否启用 */
	private boolean enabled = true;
	
	/** 根路径 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
	private String basePath;
	// 模板组
    private TemplateGroup templateGroup;
    // 默认首页模板
    private Template indexTemplate;
    // 默认列表模板
    private Template listTemplate;
    // 默认详细页面模板
    private Template detailTemplate;
    // 默认搜索页面模板
    private Template searchTemplate;
    // 配色方案
	@Size(max=300)
	@javax.persistence.Column(length=300)
    private String colors;
	
	/** 流程 */
	private Workflow workflow;
	
	/** 管理角色 */
	@ManyToMany
	private Set<Group> groups = CollectUtils.newHashSet();
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public Set<Group> getGroups() {
		return groups;
	}
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
	public TemplateGroup getTemplateGroup() {
		return templateGroup;
	}
	public void setTemplateGroup(TemplateGroup templateGroup) {
		this.templateGroup = templateGroup;
	}
	public Template getIndexTemplate() {
		return indexTemplate;
	}
	public void setIndexTemplate(Template indexTemplate) {
		this.indexTemplate = indexTemplate;
	}
	public Template getListTemplate() {
		return listTemplate;
	}
	public void setListTemplate(Template listTemplate) {
		this.listTemplate = listTemplate;
	}
	public Template getDetailTemplate() {
		return detailTemplate;
	}
	public void setDetailTemplate(Template detailTemplate) {
		this.detailTemplate = detailTemplate;
	}
	public Template getSearchTemplate() {
		return searchTemplate;
	}
	public void setSearchTemplate(Template searchTemplate) {
		this.searchTemplate = searchTemplate;
	}
	public String getColors() {
		return colors;
	}
	public void setColors(String colors) {
		this.colors = colors;
	}
	public Workflow getWorkflow() {
		return workflow;
	}
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

}
