package org.beangle.wenjuan.action;

import java.util.Collection;
import java.util.List;

import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.wenjuan.model.Xxmb;


public class XxmbAction extends WenJuanCommonAction{
	
	@Override
	protected String getEntityName() {
		return Xxmb.class.getName();
	}
	
	public String index() {
		return forward();
		//直接执行search方法，跳过index页面
//		return forward(new Action(this, "search"));
	}
	
	/**
	 * 查询语句
	 */
	protected OqlBuilder<Xxmb> getQueryBuilder() {
		//传递模板类型
//		put("template",getDictDataByDictType(DictTypeWJUtil.TEMPLATE));
		OqlBuilder<Xxmb> oql = OqlBuilder.from(Xxmb.class,getShortName());
		populateConditions(oql);
		oql.orderBy(getOrderString(getShortName() + ".mblx.id"));
		oql.limit(getPageLimit());
		return oql;
	}
	

	/**
	 * 编辑
	 */
	protected void editSetting(Entity<?> entity) {
		//传递模板类型
//		put("template",getDictDataByDictType(DictTypeWJUtil.TEMPLATE));
		super.editSetting(entity);
	}
	/**
	 * 保存
	 */
	protected String saveAndForward(Entity<?> entity) {
		Xxmb xxmb = (Xxmb) entity;
		//以下判断获取ID和模板类型ID
//		Long id = getLong("xxmb.id");
//		Long mblxId = xxmb.getMblx().getId();
//		//如果ID为空则需要设置排序字段，否则不需要设置
//		if(id == null){
//			//根据模板类型查询到该类型下有几个选项，然后根据数量获取排序标识（A,B,C,D...）
//			xxmb.setPx(CommonUtils.getIdentString(getXxmbs(mblxId)));
//		}
		entityDao.saveOrUpdate(xxmb);
		return redirect("search", "info.save.success");
	}
	
	/**
	 * 根据模板类型ID查询选项模板的数量
	 * @param mblxId 模板类型ID
	 * @return 数量
	 */
	protected int getXxmbs(Long mblxId){
		List<Xxmb> xxmbs = findXxmbByMblx(mblxId);
		int count = 0;
		if(xxmbs != null && xxmbs.size() > 0){
			count = xxmbs.size();
		}
		return count;
	}
	
	protected List<Xxmb> findXxmbByMblx(Long mblxId){
		OqlBuilder<Xxmb> oql = OqlBuilder.from(Xxmb.class,"xxmb");
		if(mblxId != null){
			oql.where("xxmb.mblx.id =:mblxId",mblxId);
		}
		return entityDao.search(oql);
	}
	
	@Override
	protected String removeAndForward(Collection<?> entities) {
//		List<Xxmb> xxmbs = (List<Xxmb>) entities;
//		for (Iterator<Xxmb> iterator = xxmbs.iterator(); iterator.hasNext();) {
//			Xxmb xxmb = iterator.next();
//			Long mblxId = xxmb.getMblx().getId();
//			String ident = xxmb.getPx();
//			List<Xxmb> xxmbList = findXxmbByMblx(mblxId);
//			
//		}
		return super.removeAndForward(entities);
	}

}
