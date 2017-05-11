package org.beangle.wenjuan.service;

import java.util.List;

import org.beangle.website.common.action.FileAction;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.model.WenJuan;
import org.beangle.wenjuan.model.Wjtm;
import org.beangle.wenjuan.model.Xxmb;

public interface WenJuanService {

	/**
	 * 根据随机题目设置查询随机题目
	 * @param wenJuan
	 */
	public void findSjtm(WenJuan wenJuan);
	/**
	 * 查询问卷题目
	 * 随机题目保存在设置里
	 * @param wenJuan
	 * @return 固定问卷题目
	 */
	public List<TiMu> getTiMu(WenJuan wenJuan);

	/**
	 * 查询问卷题目
	 * 随机题目保存在设置里
	 * @param wenJuan
	 * @return 固定问卷题目
	 */
	public List<Wjtm> getWjtm(WenJuan wenJuan);
	
	/**
	 * 更新题库的题目数量
	 */
	public void updateTikuTimuNum();
	public void updateTikuTimuNum(Long tiKuId);
	
	/**
	 * 查询所有选项模板
	 * @return
	 */
	public List<Xxmb> findXxmb();

	public String saveTimu(TiMu tm, FileAction action);
	
	public List<Wjtm> findWjtm(WenJuan wenJuan);
	
}
