package org.beangle.wenjuan.service;

import java.util.List;
import java.util.Map;

import org.beangle.website.system.model.DictTree;
import org.beangle.wenjuan.model.TiKu;
import org.beangle.wenjuan.model.TiMu;

/**
 * 问卷同步接口
 * @author CHII
 *
 */
public interface WenJuanSyncService {
	
	/**
	 * 根据代码获得题库，建议代码规则：模块代码_对象ID，如：ZYK_KC_1
	 * @param code 最大长度30
	 * @return
	 */
	public TiKu getTiKu(String code);

	
	/**
	 * 根据题库查找题库关联的题目分类
	 * @param tiKu 
	 * @return 返回的题库关联的题目分类
	 */
	public DictTree getTmfl(TiKu tiKu);

	
	/**
	 * 根据代码获得题目分类，建议代码规则：模块代码_对象ID，如：ZYK_KC_1
	 * @param tiKu 根据题库查找题目分类
	 * @return 返回的题目分类的上级分类默认为代码为TMFL的字典树
	 */
	public DictTree getTmfl(String code);
	
	/**
	 * 添加一个题目分类
	 * @param dictTree
	 * @param parent
	 * @param indexno
	 */
	public void addTmfl(DictTree dictTree, DictTree parent, int indexno);

	/**
	 * 根据题库，题目适用范围代码，查询每个分类的题目数
	 * @param tiKu
	 * @param syfwCode
	 * @return
	 */
	public Map<String, Long> getTmflCount(TiKu tiKu, String syfwCode);

	/**
	 * 根据题目分类，题目适用范围代码查询题目
	 * @param tmfl
	 * @param syfwCode
	 * @return
	 */
	public List<TiMu> findTiMu(DictTree tmfl, String syfwCode);

}
