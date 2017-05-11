package org.beangle.wenjuan.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.ems.security.Group;
import org.beangle.wenjuan.model.TiKu;

public interface TiKuService {
	
	/**
	 * 查询有效库题
	 * @return
	 */
	public List<TiKu> findTiKu();

	/**
	 * 查询有效题库
	 * @return
	 */
	public Map<String, TiKu> getTiKuMap();
	
	/**
	 * 查询有权限的题库
	 * @param isAdmin 是否是管理员
	 * @param groups 角色
	 * @return
	 */
	public List<TiKu> findYQXTiKu(boolean isAdmin, Set<Group> groups, Long userid);
	
	/**
	 * 更新所有题库的题目数量
	 */
	public void updateTmsl();
}
