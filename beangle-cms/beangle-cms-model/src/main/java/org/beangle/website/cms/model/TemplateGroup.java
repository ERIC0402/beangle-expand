/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.model;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

/**
 * 模板组
 * @author CHII
 */
@Entity(name="org.beangle.website.cms.model.TemplateGroup")
public class TemplateGroup extends LongIdObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8895339535407749097L;

	/** 模板组名称 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
    private String name;
	
    /** 模板组介绍 */
	@Size(max=900)
	@javax.persistence.Column(length=900)
    private String introduce;
	
    /** 预览图 */
	@Size(max=300)
	@javax.persistence.Column(length=300)
    private String img;
	
    /** 是否启用 */
    private Boolean enabled = Boolean.TRUE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
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
}
