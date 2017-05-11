package org.beangle.website.workflow.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;
/**
 * 任务节点跳转
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.workflow.model.TaskNodeDecision")
public class TaskNodeDecision extends LongIdObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8507785573133636359L;

	/**
	 * 对应节点
	 */
	private TaskNode taskNode;
	
	/**
	 * 跳转序号，若为空默认跳转到下一节点
	 */
	@Size(max=8)
	@Column(length=8)
	private String gotoOrder;
	
	/**
	 * 标识
	 */
	@Size(max=8)
	@Column(length=8)
	private String value;
	
	public String getGotoOrder() {
		return gotoOrder;
	}
	public void setGotoOrder(String gotoOrder) {
		this.gotoOrder = gotoOrder;
	}
	
	public TaskNode getTaskNode() {
		return taskNode;
	}
	public void setTaskNode(TaskNode taskNode) {
		this.taskNode = taskNode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
