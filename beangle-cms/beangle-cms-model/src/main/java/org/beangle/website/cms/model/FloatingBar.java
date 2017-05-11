package org.beangle.website.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.cms.model.Site;
import org.beangle.website.system.model.DictData;

/**
 * 漂浮条
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.cms.model.FloatingBar")
public class FloatingBar extends LongIdObject {

	/**
	 * 名称
	 */
	@Size(max=300)
	@Column(length=300)
	private String name;

	/**
	 * 所属站点
	 */
	private Site site;

	/**
	 * 所在位置
	 */
	private DictData szwz;

	/**
	 * 漂浮条格式（类似栏目入口）
	 */
	@Size(max=300)
	@Column(length=300)
	private String pftgs;

	/**
	 * url
	 */
	@Size(max=300)
	@Column(length=300)
	private String url;

	/**
	 * 图片路径
	 */
	@Size(max=300)
	@Column(length=300)
	private String filePath;

	/**
	 * 图片名称
	 */
	@Size(max=300)
	@Column(length=300)
	private String fileName;

	/**
	 * 是否可见
	 */
	private boolean visible;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public DictData getSzwz() {
		return szwz;
	}

	public void setSzwz(DictData szwz) {
		this.szwz = szwz;
	}

	public String getPftgs() {
		return pftgs;
	}

	public void setPftgs(String pftgs) {
		this.pftgs = pftgs;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
