package org.beangle.website.workflow.model;


import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.Group;
import org.beangle.model.pojo.LongIdObject;

/**
 * 流程
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.workflow.model.Workflow")
public class Workflow extends LongIdObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3531405117101004444L;

	/** 代码*/
	@Size(max=64)
	@Column(length=64)
	private String code;
	
	/** 名称*/
	@Size(max=300)
	@Column(length=300)
	private String name;
	
	/**描述 */
	@Size(max=300)
	@Column(length=300)
	private String description;
	
	/**
	 * 维护人员角色
	 */
	@ManyToMany
	private Set<Group> roles = CollectUtils.newHashSet();
	
	/**
	 * 类型
	 */
	private int type;
	
	/**
	 * 流程固定
	 */
	public static final int TYPE_FIXATION = 0;
	
	/**
	 * 流程不固定
	 */
	public static final int TYPE_UNFIXATION = 1;
	
	/**
	 * 步骤节点
	 */
	@OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL)
	@OrderBy("order")
	private List<StepNode> nodes = CollectUtils.newArrayList();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public Set<Group> getRoles() {
		return roles;
	}

	public void setRoles(Set<Group> roles) {
		this.roles = roles;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<StepNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<StepNode> nodes) {
		this.nodes = nodes;
	}

	
}
