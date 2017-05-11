package org.beangle.website.expand.action;




import java.util.List;

import org.beangle.model.query.builder.OqlBuilder;

import org.beangle.website.common.action.FileAction;
import org.beangle.website.system.model.DictData;
import com.opensymphony.xwork2.ActionContext;

public class CommonAction extends FileAction {

	/**
	 * 根据字典类型ID查询字典数据
	 * @param typeId 字典类型ID
	 * @return
	 */
	protected List<DictData> getDictDataByDictType(Long typeId){
		OqlBuilder<DictData> query = OqlBuilder.from(DictData.class,"d");
		query.where("d.enabled=1");
		query.where("d.dictType.id=:dictType",typeId);
		query.orderBy("d.code");
		return entityDao.search(query);
	}
	
	/**
	 * 获取用户当前身份
	 * 
	 * @return
	 */
	protected Long getCurrentCategoryId() {
		Long curCategoryId = (Long) ActionContext.getContext().getSession().get("security.categoryId");
		return curCategoryId;
	}

	/**
	 * 是否是站群管理员身份
	 * 
	 * @return
	 */
	protected boolean isAllSitesAdmin() {
		Long curCategoryId = getCurrentCategoryId();
		if (curCategoryId != null && curCategoryId.equals(3L)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是站点管理员身份
	 * 
	 * @return
	 */
	protected boolean isOneSiteAdmin() {
		Long curCategoryId = getCurrentCategoryId();
		if (curCategoryId != null && curCategoryId.equals(2L)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是普通用户身份
	 * 
	 * @return
	 */
	protected boolean isGeneralUser() {
		Long curCategoryId = getCurrentCategoryId();
		if (curCategoryId != null && curCategoryId.equals(1L)) {
			return true;
		}
		return false;
	}

}
