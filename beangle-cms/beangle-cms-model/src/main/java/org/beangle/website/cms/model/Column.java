package org.beangle.website.cms.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.system.model.DictData;
import org.beangle.website.workflow.model.Workflow;

/**
 * 栏目
 * @author DONHKO
 * 
 */
@Entity(name="org.beangle.website.cms.model.Column")
public class Column extends LongIdObject {

    /**
     *
     */
    private static final long serialVersionUID = 6249389218099700256L;
    
    /** 名称 */
    @Size(max=300)
    @javax.persistence.Column(length=300)
    private String name;
    
    /** 排序 */
    @Size(max=300)
    @javax.persistence.Column(length=300)
    private String orders;
    
    /** 类型 */
    private DictData type;
    
    /** 流程 */
    private Workflow workflow;
    
    /** 父栏目 */
    private Column columns;
    
    /** 内容来源 */
    private DictData contentSource;
    
    /** 是否启用 */
    private boolean enabled = true;
    
    /** 访问权限 */
    private DictData access;
    
    /** 显示样式 */
    private DictData displayStyle;
    
    /** 栏目显示模板 */
    private Template columnTemplate;
    
    /** 信息显示模板 */
    private Template contentTemplate;
    
    /** URL地址 */
    @Size(max=300)
    @javax.persistence.Column(length=300)
    private String url;
    
    /** 扩展属性属性来源 */
    private DictData extProperty;

    /** 动态入口 */
//    @Size(max=300)
//    @javax.persistence.Column(length=300)
//    private String entry;
    private Entry entry;
    
    /** 是否继承父栏目流程 */
    private boolean doesImpl = true;
    
    /** 所属站点 */
    private Site site;
    
    /** 是否根节点 */
    private boolean node;
    
    /** 栏目扩展属性 */
    @ManyToMany
    private Set<ExtProperty> extpropertys = CollectUtils.newHashSet();
    
    /** 关联栏目 */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="CMS_RELATION_COLUMNS",          //这里的name是数据库中间表名
    		joinColumns={@JoinColumn(name="COLUMN_ID")}, //本表在中间表的外键名称	
    		inverseJoinColumns={@JoinColumn(name="RELATION_COLUMN_ID")})
    private Set<Column> relationColumns = CollectUtils.newHashSet();
    
    /** 是否可见 */
    private boolean visible = true;
    
    /**新闻类型 */
    private DictData newsType;
    
    /** 栏目权限 */
    @OneToMany(mappedBy="column", cascade = CascadeType.ALL)
    private Set<GroupColumn> groups = CollectUtils.newHashSet();
    
    public int getDepth() {
		return (null == columns) ? 1 : columns.getDepth() + 1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<GroupColumn> getGroups() {
		return groups;
	}

	public void setGroups(Set<GroupColumn> groups) {
		this.groups = groups;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public DictData getType() {
		return type;
	}

	public void setType(DictData type) {
		this.type = type;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Column getColumns() {
		return columns;
	}

	public void setColumns(Column columns) {
		this.columns = columns;
	}

	public DictData getContentSource() {
		return contentSource;
	}

	public void setContentSource(DictData contentSource) {
		this.contentSource = contentSource;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public DictData getAccess() {
		return access;
	}

	public void setAccess(DictData access) {
		this.access = access;
	}

	public DictData getDisplayStyle() {
		return displayStyle;
	}

	public void setDisplayStyle(DictData displayStyle) {
		this.displayStyle = displayStyle;
	}

	public Template getColumnTemplate() {
		return columnTemplate;
	}

	public void setColumnTemplate(Template columnTemplate) {
		this.columnTemplate = columnTemplate;
	}

	public Template getContentTemplate() {
		return contentTemplate;
	}

	public void setContentTemplate(Template contentTemplate) {
		this.contentTemplate = contentTemplate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DictData getExtProperty() {
		return extProperty;
	}

	public void setExtProperty(DictData extProperty) {
		this.extProperty = extProperty;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public boolean isDoesImpl() {
		return doesImpl;
	}

	public void setDoesImpl(boolean doesImpl) {
		this.doesImpl = doesImpl;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public boolean isNode() {
		return node;
	}

	public void setNode(boolean node) {
		this.node = node;
	}

	public Set<ExtProperty> getExtpropertys() {
		return extpropertys;
	}

	public void setExtpropertys(Set<ExtProperty> extpropertys) {
		this.extpropertys = extpropertys;
	}

	public Set<Column> getRelationColumns() {
		return relationColumns;
	}

	public void setRelationColumns(Set<Column> relationColumns) {
		this.relationColumns = relationColumns;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public DictData getNewsType() {
		return newsType;
	}

	public void setNewsType(DictData newsType) {
		this.newsType = newsType;
	}
}
