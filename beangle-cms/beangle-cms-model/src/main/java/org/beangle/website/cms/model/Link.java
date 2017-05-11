package org.beangle.website.cms.model;

import java.io.File;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.system.model.DictData;

/**
 * 友情链接
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.cms.model.Link")
public class Link extends LongIdObject {

    //名称
	@Size(max=100)
	@javax.persistence.Column(length=100)
    private String name;
	
    //排序
	@Size(max=100)
	@javax.persistence.Column(length=100)
    private String orders;
	
    //URL
	@Size(max=300)
	@javax.persistence.Column(length=300)
    private String url;
	
    //图片
	@Size(max=300)
	@javax.persistence.Column(length=300)
    private String img;

    //描述
	@Size(max=300)
	@javax.persistence.Column(length=300)
    private String describe;
    
    //是否启用
    private Boolean enabled = Boolean.TRUE;
    
    //所属站点_类型
    private LinksType linkType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

	public LinksType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinksType linkType) {
		this.linkType = linkType;
	}

}
