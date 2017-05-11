package org.beangle.website.cms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
/**
 * 上传文件
 * @author DONHKO
 *
 */
@Entity(name="org.beangle.website.cms.model.Annex")
public class Annex extends LongIdObject {

	/**
	 */
	private static final long serialVersionUID = 5979265721672955142L;

	/** 文件名 */
	@Size(max=1200)
	@Column(length=1200)
	private String fileName;
	
	/** 文件大小 */
	private Long fileSize;
	
	/** 文件路径*/
	@Size(max=300)
	@Column(length=300)
	private String filePath;
	
	/** 上传时间 */
	private Date time;

	/** 是否公开 */
	private boolean open = false;
	
	/** 上传人 */
	private User user;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
