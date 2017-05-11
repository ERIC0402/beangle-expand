/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.system.model.DictData;

/**
 * 组件
 * @author CHII
 */
@Entity(name="org.beangle.website.cms.model.Widget")
public class Widget extends LongIdObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5037567503168909589L;

	/** 组件名称 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
    private String name;
	
    /** 类型 */
    private DictData type;
    
    /** 组件类名 */
    @Size(max=300)
    @javax.persistence.Column(length=300)
    private String className;
    
    /** 预览图 */
    @Size(max=300)
    @javax.persistence.Column(length=300)
    private String img;
    
    /** 是否启用 */
    private Boolean enabled = Boolean.TRUE;
    
    /** 关联模板组 */
    @ManyToMany
    private Set<TemplateGroup> groups = CollectUtils.newHashSet();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DictData getType() {
		return type;
	}

	public void setType(DictData type) {
		this.type = type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<TemplateGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<TemplateGroup> groups) {
		this.groups = groups;
	}
}
