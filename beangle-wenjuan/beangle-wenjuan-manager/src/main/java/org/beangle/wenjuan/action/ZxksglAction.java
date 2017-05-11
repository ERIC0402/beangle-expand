package org.beangle.wenjuan.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.commons.exception.MyException;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.wenjuan.model.KsapParent;
import org.beangle.wenjuan.model.Wjjg;
import org.beangle.wenjuan.model.Zxks;
import org.beangle.wenjuan.model.ZxksParent;
import org.beangle.wenjuan.service.WjjgService;

/**
 * 网上评教
 * 
 * @author GZW
 * 
 */
public class ZxksglAction extends BaseCommonAction {
	
	protected WjjgService wjjgService;
	
	public void setWjjgService(WjjgService wjjgService) {
		this.wjjgService = wjjgService;
	}

	@Override
	protected String getEntityName() {
		return Zxks.class.getName();
	}
	
	public String index() {
		return forward();
	}
	
	/**
	 * 查找信息action.
	 * 
	 * @return
	 */
	public String search() {
		OqlBuilder<ZxksParent> query = getQueryBuilder();
		List<ZxksParent> zxkss = entityDao.search(query);
		
		put("zxkss", zxkss);
		put("userid", getUserId());
		
		return forward();
	}

	/**
	 * 查询语句
	 */
	protected OqlBuilder<ZxksParent> getQueryBuilder() {
		@SuppressWarnings("unchecked")
		OqlBuilder<ZxksParent> oql = (OqlBuilder<ZxksParent>) super.getQueryBuilder();
		Long zxksid=getLong("zxksid");
		
		if(zxksid!=null){
			oql.where(getShortName()+".ksap.id=:zxksid",zxksid);
			
		}
		if(!isAdmin()){
			oql.join(getShortName()+".task.teachers","te");
			oql.where("te.id=:teacherid",getUserId());
		}
	
		
		put("zxksid",zxksid);
		return oql;
	}
	
	/**
	 * 计算成绩
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String jscj(){
		try {
			Collection<ZxksParent> zxkss = null;
			Long[] ids = getEntityIds(getShortName());
			if(ids != null && ids.length > 0){
				zxkss = getModels(getEntityName(), ids);
			}else{
				OqlBuilder<ZxksParent> oql = getQueryBuilder();
				oql.limit(null);
				zxkss = entityDao.search(oql);
			}
			for(ZxksParent zxks : zxkss){
				try {
					wjjgService.calScore(zxks.getWjjg());
				} catch (Exception e) {
				}
			}
			return redirect("search", "计算成功");
		} catch(MyException e){
			logger.error("saveAndForwad failure", e);
			return redirect("search", e.getMessage());
		}catch (Exception e) {
			logger.info("saveAndForwad failure", e);
			return redirect("search", "计算失败");
		}
	}
	
	@Override
	protected String removeAndForward(Collection<?> entities) {
		String result = super.removeAndForward(entities);
		try {
			@SuppressWarnings("unchecked")
			Collection<ZxksParent> zxkss = (Collection<ZxksParent>) entities;
			for(ZxksParent zxks : zxkss){
				entityDao.remove(zxks.getWjjg());
			}
		} catch (Exception e) {
			logger.error("删除问卷结果出错", e);
		}
		return result;
	}
	
	/**
	 *  保存分数
	 * @return
	 */
	public String saveSzfs(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("score", getFloat("score"));
		params.put("ids", StrUtils.splitToLong(get("ids")));
		
		String entityName=getEntityName();
		String shorName=getShortName();
//		entityDao.executeUpdateHql("update " + getEntityName() + " set score = :score where id in (:ids)", params);
		entityDao.executeUpdateHql("update " +Wjjg.class.getName() +" t   set t.score = :score  where t.id in (select zxks.wjjg.id from " +ZxksParent.class.getName()+" zxks where zxks.id in (:ids))  ", params);
		
		return forward();
	}
}
