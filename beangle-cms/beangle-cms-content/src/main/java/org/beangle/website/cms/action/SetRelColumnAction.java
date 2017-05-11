package org.beangle.website.cms.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.ColumnContent;
import org.beangle.website.cms.model.Content;

import org.beangle.website.common.util.SeqStringUtil;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictTypeUtils;

public class SetRelColumnAction extends ContentCommonAction {

	@Override
	protected String getShortName() {
		return "cc";
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		Content content = entityDao.get(Content.class, getLong("content.id"));
		put("content",content);
		OqlBuilder<ColumnContent> oql = OqlBuilder.from(ColumnContent.class,"cc");
		oql.where("cc.content.id=:contentId",content.getId());
		oql.orderBy(getOrderString("cc.id"));
		oql.limit(getPageLimit()); 
		return oql;
	}
	
	public String edit(){
		ColumnContent cc = getEntity(ColumnContent.class,"cc");
		if(cc.getId() == null){
			Long contentId = getLong("content.id");
			Content content = entityDao.get(Content.class, contentId);
			cc.setContent(content);
		}
		
		put("cc",cc);
		put("purviews",getDictDataByDictType(DictTypeUtils.READ_PURVIEW));
		put("properties",getDictDataByDictType(DictTypeUtils.CONTENT_PROPERTY));
		return forward();
	}
	
	public String save(){
		ColumnContent cc = populateEntity(ColumnContent.class,"cc");
		Content content = entityDao.get(Content.class, getLong("cc.content.id"));
		if(cc.getId() == null){
			Set<ColumnContent> sets = content.getColumns();
			boolean doesMain = false;
			put("cc",cc);
			for (Iterator<ColumnContent> it = sets.iterator(); it.hasNext();) {
				ColumnContent c = it.next();
				
				if (c.getColumn().getId().equals(cc.getColumn().getId())) {
					return redirect("edit", "columns.exist","content.id="+getLong("cc.content.id"));
				}
				if(c.isDoesMainColumn() && !c.getColumn().getId().equals(cc.getColumn().getId())){
					doesMain = true;
				}
				if (cc.isDoesMainColumn() && c.isDoesMainColumn()) {
					return redirect("edit", "main.columns.exist","content.id="+getLong("cc.content.id"));
				}
			}
			if(!doesMain){
				return redirect("edit","唯一的主栏目不可修改为非主栏目","content.id="+getLong("cc.content.id"));
			}
		}
		List<DictData> propertys = entityDao.get(DictData.class,getAll("propertyIds",Long.class));
		cc.getContentProperties().clear();
		cc.getContentProperties().addAll(propertys);
		Date topStartDate = getDateTime("cc.topStartDate");
		if(topStartDate == null){
			cc.setTopStartDate(new Date());
		}
		entityDao.saveOrUpdate(cc);
		return redirect("search","info.save.success","content.id="+content.getId());
	}
	
	public String remove(){
		String id = get("cc.ids");
		Long[] ids = SeqStringUtil.transformToLong(id);
		Long contentId = null;
		for (int i = 0; i < ids.length; i++) {
			ColumnContent cc = (ColumnContent) entityDao.get(ColumnContent.class, ids[i]);
			if(contentId == null){
				contentId = cc.getContent().getId();
			}
			entityDao.remove(cc);
		}
		saveOperationLog("内容管理-待处理", "删除了关联栏目"+ids);
		return redirect("search", "info.delete.success","content.id="+contentId);
	}
}
