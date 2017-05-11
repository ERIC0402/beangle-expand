package org.beangle.wenjuan.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictTree;

/**
 * 题库
 * 
 * @author Donhko
 * 
 */
@Entity(name = "org.beangle.wenjuan.model.TiKu")
public class TiKu extends LongIdObject {

	/**
	 * 题库名称
	 */
	@Size(max = 300)
	@Column(length = 300)
	private String tkmc;
	/**
	 * 代码
	 */
	@Column(length=30)
	private String code;
	/**
	 * 备注
	 */
	@Column(length = 300)
	private String remark;

	/**
	 * 题库分类
	 */
	private DictTree tkfl;
	
	/**
	 * 创建人
	 */
	private User createUser;

	/**
	 * 管理角色
	 */
	@ManyToMany
	private Set<Group> groups = CollectUtils.newHashSet();
	
	/**
	 * 管理人员
	 */
	@ManyToMany
	private Set<User> users = CollectUtils.newHashSet();

	/**
	 * 是否有效
	 */
	private boolean enabled = true;

	/**
	 * 题目数量
	 */
	private Integer tmsl = 0;

	/**
	 * 包含所有题目
	 */
	@OneToMany(mappedBy = "sstk", cascade = CascadeType.ALL)
	private Set<TiMu> tms = CollectUtils.newHashSet();

	public TiKu() {
		super();
	}

	public TiKu(Long id) {
		super(id);
	}

	public String getTkmc() {
		return tkmc;
	}

	public void setTkmc(String tkmc) {
		this.tkmc = tkmc;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getTmsl() {
		if (tmsl == null) {
			tmsl = 0;
		}
		return tmsl;
	}

	public void setTmsl(Integer tmsl) {
		this.tmsl = tmsl;
	}

	public Set<TiMu> getTms() {
		return tms;
	}

	public void setTms(Set<TiMu> tms) {
		this.tms = tms;
	}

	public DictTree getTkfl() {
		return tkfl;
	}

	public void setTkfl(DictTree tkfl) {
		this.tkfl = tkfl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
