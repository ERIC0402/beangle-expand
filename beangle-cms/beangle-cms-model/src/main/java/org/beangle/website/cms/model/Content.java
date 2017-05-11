package org.beangle.website.cms.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;

import org.beangle.website.system.model.DictData;

/**
 * 信息
 * 
 * @author DONHKO
 * 
 */
@Entity(name = "org.beangle.website.cms.model.Content")
public class Content extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1209878734287382408L;

	/** 标题 */
	@Size(max = 600)
	private String title;

	/** 标题颜色 */
	@Size(max = 8)
	private String titleColor;

	/** 标题是否加粗 */
	private boolean doesBold = false;

	/** 副标题 */
	@Size(max = 600)
	private String subTitle;

	/** 关键字 */
	@Size(max = 600)
	private String keyword;

	/** 摘要 */
	@Size(max = 1500)
	private String abstracts;

	/** 起草日期 */
	private Date draftDate;

	/** 内容样式 */
	private DictData contentStyle;

	/** URL */
	@Size(max = 600)
	private String url;

	/** 内容 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "CLOB", length = 1048576000)
	private String detail;

	/** 关联栏目 */
	@OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
	private Set<ColumnContent> columns = CollectUtils.newHashSet();

	/** 起草人 */
	private User drafter;

	/** 标题图片 */
	@Size(max = 600)
	private Annex titleImage;

	/** 附件 */
	@ManyToMany
	private Set<Annex> annexs = CollectUtils.newHashSet();

	/** 起草人IP */
	@Size(max = 300)
	private String ipAddr;

	private Long readTimes;
    
    /** 访问权限 */
    private DictData access;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public Date getDraftDate() {
		return draftDate;
	}

	public void setDraftDate(Date draftDate) {
		this.draftDate = draftDate;
	}

	public DictData getContentStyle() {
		return contentStyle;
	}

	public void setContentStyle(DictData contentStyle) {
		this.contentStyle = contentStyle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Set<ColumnContent> getColumns() {
		return columns;
	}

	public void setColumns(Set<ColumnContent> columns) {
		this.columns = columns;
	}

	public User getDrafter() {
		return drafter;
	}

	public void setDrafter(User drafter) {
		this.drafter = drafter;
	}

	public Annex getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(Annex titleImage) {
		this.titleImage = titleImage;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Set<Annex> getAnnexs() {
		return annexs;
	}

	public void setAnnexs(Set<Annex> annexs) {
		this.annexs = annexs;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	public boolean isDoesBold() {
		return doesBold;
	}

	public void setDoesBold(boolean doesBold) {
		this.doesBold = doesBold;
	}

	public Long getReadTimes() {
		return readTimes;
	}

	public void setReadTimes(Long readTimes) {
		this.readTimes = readTimes;
	}

	public DictData getAccess() {
		return access;
	}

	public void setAccess(DictData access) {
		this.access = access;
	}
}
