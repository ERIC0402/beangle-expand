package org.beangle.website.cms.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.ems.security.Group;
import org.beangle.model.pojo.LongIdObject;

/**
 * 栏目权限
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.cms.model.GroupColumn")
public class GroupColumn extends LongIdObject {

	/** 角色 */
	@NotNull
	private Group group;
	
	/** 栏目 */
	@NotNull
	private Column column;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}
	
}
