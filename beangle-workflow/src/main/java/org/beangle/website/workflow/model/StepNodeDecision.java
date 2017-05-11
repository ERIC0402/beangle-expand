package org.beangle.website.workflow.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

/**
 * 步骤节点跳转
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.workflow.model.StepNodeDecision")
public class StepNodeDecision extends LongIdObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4950783593022777699L;

	/**
	 * 对应节点
	 */
	private StepNode stepNode;
	
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
	
	public StepNode getStepNode() {
		return stepNode;
	}
	public void setStepNode(StepNode stepNode) {
		this.stepNode = stepNode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
