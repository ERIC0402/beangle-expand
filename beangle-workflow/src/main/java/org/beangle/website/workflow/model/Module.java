package org.beangle.website.workflow.model;


import java.util.HashSet;
import java.util.Set;

import org.beangle.model.pojo.LongIdObject;

/**
 * 模块类
 * @author DONHKO
 *
 */
public class Module extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2367621893235738651L;
	
	/**
	 * 模块名称
	 */
	private String name;
	
	/**
	 * 模块代码
	 */
	private String code;
	
	/**
	 * 关联流程
	 */
	private Set workflows = new HashSet();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set getWorkflows() {
		return workflows;
	}

	public void setWorkflows(Set workflows) {
		this.workflows = workflows;
	}
	
	
}
