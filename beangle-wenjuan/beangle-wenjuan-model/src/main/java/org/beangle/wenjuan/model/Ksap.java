package org.beangle.wenjuan.model;

import javax.persistence.Entity;

/**
 * 考试安排
 * 
 * @author CHII
 * 
 */

@Entity(name = "org.beangle.wenjuan.model.Ksap")
public class Ksap extends KsapParent {

	/** 参加考试的班级 */
//	@ManyToMany
//	private Set<Clazz> users = CollectUtils.newHashSet();
}
