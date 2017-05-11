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
 * 任务
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.workflow.model.Task")
public class Task extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -532138394228005974L;
	/**
	 * 代码
	 */
	@Size(max=8)
	@Column(length=8)
	private String code;
	/**
	 * 维护人员角色
	 */
	@ManyToMany
	private Set<Group>  roles =  CollectUtils.newHashSet();
	/**
	 * 任务名称
	 */
	@Size(max=600)
	@Column(length=600)
	private String name;
	/**
	 * 状态
	 */
	private int state;
	/**
	 * 进行中状态
	 */
	public static final int DOING_STATE=0;
	/**
	 * 已完成状态
	 */
	public static final int FINISH_STATE=1;
	/**
	 * 当前节点
	 */
	private TaskNode currentNode;
	
	/**
	 * 步骤节点
	 */
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	@OrderBy("order")
	private List<TaskNode> nodes = CollectUtils.newArrayList();
	

	public TaskNode getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(TaskNode currentNode) {
		this.currentNode = currentNode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Set<Group> getRoles() {
		return roles;
	}

	public void setRoles(Set<Group> roles) {
		this.roles = roles;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<TaskNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<TaskNode> nodes) {
		this.nodes = nodes;
	}

}
