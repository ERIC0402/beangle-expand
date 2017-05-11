/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

/**
 *
 * @author CHII
 */
@Entity(name="org.beangle.website.cms.model.WidgetConfig")
public class WidgetConfig extends LongIdObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7264975613193000866L;

	/** 关联的组件 */
    private Widget widget;
    
    /** 设置 */
    @Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition="CLOB")
    private String config;
    
    /** 关联模板 */
    private Template template;
    
    /** 关联站点 */
    private TemplateGroup group;
    
    /** 组件位置:parentId */
    @Size(max=300)
    @javax.persistence.Column(length=300)
    private String position;
    
    /** 排序号 */
    private Long no;
    
    /** 关联站点 */
    private Site site;

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public TemplateGroup getGroup() {
		return group;
	}

	public void setGroup(TemplateGroup group) {
		this.group = group;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
}
