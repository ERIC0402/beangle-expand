package org.beangle.website.cms.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.system.model.DictData;

/**
 * 模板
 * @author DONHKO
 *
 */
@Entity(name="org.beangle.website.cms.model.Template")
public class Template extends LongIdObject {

    /**
     *
     */
    private static final long serialVersionUID = -322025515934477433L;
    
    /** 所属模板组 */
    private TemplateGroup group;
    
    /** 模板类型：网站首页、文档列表、文档详细、其它 */
    private DictData type;
    
    /** 名称 */
    @Size(max=300)
    @javax.persistence.Column(length=300)
    private String name;
    
    /** 缩略图 */
    @Size(max=300)
    @javax.persistence.Column(length=300)
    private String img;
    
    /** 备注  */
    @Size(max=300)
    @javax.persistence.Column(length=300)
    private String remark;
    
    /** 模板布局 */
    @Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition="CLOB")
    private String layout;
    
    /** 关联的组件配置 */
    @ManyToMany
    private Set<WidgetConfig> widgets = CollectUtils.newHashSet();
    
    /** 是否启用 */
    private Boolean enabled = Boolean.TRUE;

	public TemplateGroup getGroup() {
		return group;
	}

	public void setGroup(TemplateGroup group) {
		this.group = group;
	}

	public DictData getType() {
		return type;
	}

	public void setType(DictData type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public Set<WidgetConfig> getWidgets() {
		return widgets;
	}

	public void setWidgets(Set<WidgetConfig> widgets) {
		this.widgets = widgets;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
    
}
