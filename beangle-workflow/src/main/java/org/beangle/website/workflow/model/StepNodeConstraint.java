package org.beangle.website.workflow.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

/**
 * 步骤节点限制
 * @author GOKU
 *
 */ 
@Entity(name="org.beangle.website.workflow.model.StepNodeConstraint")
public class StepNodeConstraint extends LongIdObject { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5809750089074595440L;
	/**
	 * 对应节点
	 */
	private StepNode stepNode;
	/**
	 * 代码
	 */
	@Size(max=8)
	@Column(length=8)
	private String code;
	/**
	 * 限制通过人数
	 */
	public static final String CONSTRAINT_PASSNUM="1";
	/**
	 * 限制完成时间
	 */
	public static final String CONSTRAINT_TIME="2";
	/**
	 * 限制人数和时间
	 */
	public static final String CONSTRAINT_PASSNUM_TIME="3";
	
	/**
	 * 指定通过人数
	 */
	private Integer passNum;
	/**
	 * 指定时间长度（单位小时）
	 */
	private Integer time;
	
	
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getPassNum() {
		return passNum;
	}

	public void setPassNum(Integer passNum) {
		this.passNum = passNum;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public StepNode getStepNode() {
		return stepNode;
	}

	public void setStepNode(StepNode stepNode) {
		this.stepNode = stepNode;
	}
}
