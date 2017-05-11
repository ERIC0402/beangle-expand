package org.beangle.website.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;
/**
 * 在线留言类型
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.cms.model.OnlineMessType")
public class OnlineMessType extends LongIdObject{

	//所属站点
	private Site site;
	
	//类型代码
	@Size(max=100)
	@Column(length=100)
	private String typeCode;
	
	//类型名称
	@Size(max=100)
	@Column(length=100)
	private String typeName;
	
	//是否启用
	private boolean enabled = true;
	
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
