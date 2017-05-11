package org.beangle.website.cms.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;

/**
 * 访问入口
 * 
 * @author CHII
 * 
 */
@Entity(name="org.beangle.website.cms.model.Entry")
public class Entry extends LongIdObject {

	/**
	 * 名称
	 */
	@Column(length=300)
	private String name;
	/**
	 * 访问入口
	 */
	@Column(length=300)
	private String url;
	/**
	 * 相关站点
	 */
	@ManyToMany
	private Set<Site> sites = CollectUtils.newHashSet();
	/**
	 * 是否启用
	 */
	private Boolean enabled = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Site> getSites() {
		return sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
