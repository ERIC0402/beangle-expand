package org.beangle.website.workflow.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
/**
 * 流程步骤节点
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.workflow.model.StepNode")
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class StepNode extends LongIdObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4711760953296311973L;
	/**
	 * 所属流程
	 */
	private Workflow workflow;
	/**
	 * 序号
	 */
	@Size(max=8)
	@Column(name="NODE_ORDER",length=8)
	private String order;
	/**
	 * 名称
	 */
	@Size(max=300)
	@Column(length=300)
	private String name;
	
	/**
	 * 指定操作角色
	 */
	@ManyToMany
	private Set<Group>  roles =  CollectUtils.newHashSet();
	
	/**
	 * 指定操作人
	 */
	private User user;

	/**
	 * 类型
	 */
	private int type;
	
	/**
	 * 节点操作的方法入口
	 */
	@Size(max=200)
	@Column(length=200)
	private String entry;
	

	/**
	 * 普通类型（无时间限制，一个人通过）
	 */
	public static final int NOMAL_TYPE=0;
	/**
	 * 跳转类型（根据传入的参数跳转到设定的节点）
	 */
	public static final int DECISION_TYPE=1;
	/**
	 * 限制类型（包括时间、通过人数、时间加通过人数）
	 */
	public static final int CONSTRAINT_TYPE=2;
	/**
	 * 跳转限制类型
	 */
	public static final int DECISION_CONSTRAINT_TYPE=3;
	
	/**
	 * 日志格式
	 */
	@Size(max=600)
	@Column(length=600)
	private String logFormat;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Group> getRoles() {
		return roles;
	}

	public void setRoles(Set<Group> roles) {
		this.roles = roles;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getLogFormat() {
		return logFormat;
	}

	public void setLogFormat(String logFormat) {
		this.logFormat = logFormat;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
