package org.beangle.wenjuan.service;

import java.util.List;

import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.model.WenJuan;

public interface WbtmService {

	/**
	 * 解析文本获得题目
	 * @param text
	 * @return
	 */
	public List<TiMu> getTiMus(String text);

	/**
	 * 添加题目
	 * @param tmlist
	 * @param wenJuan
	 */
	public void addTiMu(List<TiMu> tmlist, WenJuan wenJuan);
}
