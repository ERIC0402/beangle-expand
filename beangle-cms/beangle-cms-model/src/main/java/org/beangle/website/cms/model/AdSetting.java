package org.beangle.website.cms.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;

/**
 * 广告设置
 * 
 * @author CHII
 * 
 */
@Entity(name = "org.beangle.website.cms.model.AdSetting")
public class AdSetting extends LongIdObject {
	/**
	 * 名称
	 */
	@javax.persistence.Column(length = 300)
	private String name;
	/**
	 * 位置
	 */
	private DictData position;
	/**
	 * 内容
	 */
	@Deprecated
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "CLOB", length = 1048576000)
	private String content;
	/**
	 * 图片地址
	 */
	@javax.persistence.Column(length = 300)
	private String imgUrl;
	/**
	 * 链接
	 */
	@javax.persistence.Column(length = 300)
	private String url;
	/**
	 * 状态
	 */
	private Boolean enabled = Boolean.TRUE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DictData getPosition() {
		return position;
	}

	public void setPosition(DictData position) {
		this.position = position;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
