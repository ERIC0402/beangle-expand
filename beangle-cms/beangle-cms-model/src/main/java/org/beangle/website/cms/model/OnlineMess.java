package org.beangle.website.cms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.system.model.DictData;
/**
 * 在线留言
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.cms.model.OnlineMess")
public class OnlineMess extends LongIdObject {

	//留言人
	@Size(max=100)
	@Column(length=100)
	private String person;
	
	//留言时间
	private Date time;
	
	//联系电话
	@Size(max=100)
	@Column(length=100)
	private String linkPhone;
	
	//联系邮箱
	@Size(max=100)
	@Column(length=100)
	private String linkEmail;
	
	//类型
	private DictData type;
	
	//主题
	@Size(max=300)
	@Column(length=300)
	private String title;
	
	//内容
	@Size(max=3000)
	@Column(length=3000)
	private String content;
	
	//是否回复
	private boolean back = false;
	
	//回复内容
	@Size(max=3000)
	@Column(length=3000)
	private String backContent;
	
	//回复人
	@Size(max=100)
	@Column(length=100)
	private String backPerson;
	
	//回复时间
	private Date backTime;
	
	//是否显示
	private boolean visible = false;
	
	//是否热点
	private boolean rd = false;
	
	/** 留言类型 */
	private OnlineMessType onlineMessType;
	
	public OnlineMessType getOnlineMessType() {
		return onlineMessType;
	}
	public void setOnlineMessType(OnlineMessType onlineMessType) {
		this.onlineMessType = onlineMessType;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getLinkEmail() {
		return linkEmail;
	}
	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}
	public DictData getType() {
		return type;
	}
	public void setType(DictData type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getBackContent() {
		return backContent;
	}
	public void setBackContent(String backContent) {
		this.backContent = backContent;
	}
	public String getBackPerson() {
		return backPerson;
	}
	public void setBackPerson(String backPerson) {
		this.backPerson = backPerson;
	}
	public boolean isBack() {
		return back;
	}
	public void setBack(boolean back) {
		this.back = back;
	}
	public Date getBackTime() {
		return backTime;
	}
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isRd() {
		return rd;
	}
	public void setRd(boolean rd) {
		this.rd = rd;
	}
	
}
