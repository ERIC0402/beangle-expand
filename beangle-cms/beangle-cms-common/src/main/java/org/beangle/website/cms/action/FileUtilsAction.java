package org.beangle.website.cms.action;

import java.io.UnsupportedEncodingException;

import org.beangle.website.cms.model.Annex;
import org.beangle.website.cms.model.Link;


public class FileUtilsAction extends CommonAction {
	/**
	 * 根据附件ID获取文件
	 * @throws UnsupportedEncodingException
	 */
	public void downFileById() throws UnsupportedEncodingException{
    	Long id = getLong("fileId");
    	Boolean isShowImage = getBoolean("isShowImage");
    	Annex annex = entityDao.get(Annex.class, id);
    	getFile(annex.getFilePath(), isShowImage);
    }
	
	/**
	 * 根据友情链接获取文件
	 * @throws UnsupportedEncodingException
	 */
	public void downFileByLink() throws UnsupportedEncodingException{
    	Long id = getLong("fileId");
    	Boolean isShowImage = getBoolean("isShowImage");
    	Link link = entityDao.get(Link.class, id);
    	getFile(link.getImg(), isShowImage);
    }
}
