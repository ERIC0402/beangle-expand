package org.beangle.website.cms.model;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictType;
/**
 * 信息扩展属性
 * @author DONHKO
 *
 */
@Entity(name="org.beangle.website.cms.model.ExtProperty")
public class ExtProperty extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 排序 */
	@Size(max=30)
	@javax.persistence.Column(length=30)
	private String orders;
	
	/** 属性名-显示 */
	@Size(max=30)
	@javax.persistence.Column(length=30)
	private String propertyName;
	
	/** 字段名 */
	@Size(max=30)
	@javax.persistence.Column(length=30)
	private String fieldName;
	
	/** 是否启用 */
	private boolean enabled = false;
	
	/** 信息扩展属性类型 */
	private DictData type;
	
	/** 信息扩展属性可选范围 */
	private DictType value;

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public DictData getType() {
		return type;
	}

	public void setType(DictData type) {
		this.type = type;
	}

	public DictType getValue() {
		return value;
	}

	public void setValue(DictType value) {
		this.value = value;
	}
	
}
