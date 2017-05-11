package org.beangle.website.cms.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

@Entity(name="org.beangle.website.cms.model.TemplateLayout")
public class TemplateLayout extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 名称
     */
	@Size(max=300)
	@javax.persistence.Column(length=300)
    private String name;
    /**
     * 预览图
     */
	@Size(max=600)
	@javax.persistence.Column(length=600)
    private String img;
    /**
     * 模板内容
     */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition="CLOB",length=1048576000)
    private String content;
    /**
     * 是否使用
     */
    private Boolean enabled;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
