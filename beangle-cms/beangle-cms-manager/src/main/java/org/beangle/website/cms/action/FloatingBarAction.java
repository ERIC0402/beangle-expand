package org.beangle.website.cms.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.model.FloatingBar;
import org.beangle.website.cms.utils.DictTypeUtils;

import org.beangle.website.cms.model.Site;


public class FloatingBarAction extends ExpandCommonAction {

	public String index() {
		putDictDatas();
		return forward();
	}

	
	/**
	 * 传值
	 */
	protected void putDictDatas(){
		put("szwzs",getDictDataByDictType(DictTypeUtils.SZWZ)); //显示位置
		put("sites",getDictDataByDictType());
		
	}
	
	protected List<Site> getDictDataByDictType(){
		OqlBuilder<Site> query = OqlBuilder.from(Site.class,"s");
		query.where("s.enabled =:enabled",true);
		query.orderBy("s.code");
		return entityDao.search(query);
	}
	
	/**
	 * 查询语句
	 */
	protected OqlBuilder<FloatingBar> getQueryBuilder() {
		OqlBuilder<FloatingBar> oql = OqlBuilder.from(FloatingBar.class,"floatingBar");
		populateConditions(oql);
		String order = get(Order.ORDER_STR);
		if(StringUtils.isEmpty(order)){
			order = "floatingBar.id";
		}
		oql.orderBy(order);
		oql.limit(getPageLimit());
		return oql;
	}

	/**
	 * 编辑
	 */
	protected void editSetting(Entity<?> entity) {
		FloatingBar floatingBar = (FloatingBar) entity;
		putDictDatas();
		put("floatingBar",floatingBar);
		
	}
	
	/**
	 * 保存
	 */
	protected String saveAndForward(Entity<?> entity) {
		FloatingBar floatingBar = (FloatingBar) entity;
		
		//照片更改删除旧照片
		String oldFilePath = get("oldFilePath");
        String tempFilePath = floatingBar.getFilePath();
        String newFilePath = moveAndRemoveAnnex(tempFilePath,oldFilePath);
        floatingBar.setFilePath(newFilePath);
        
	    entityDao.saveOrUpdate(floatingBar);
		return redirect("search", "info.save.success");
	}
	
	/**
	 * 删除
	 */
    public String remove()
    {
        Long floatingBarIds[] = getEntityIds();
        boolean flag = false;
        for(int i = 0; i < floatingBarIds.length; i++)
        {
        	FloatingBar floatingBars = (FloatingBar)entityDao.get(FloatingBar.class, floatingBarIds[i]);
            try
            {
                entityDao.remove(floatingBars);
            }
            catch(Exception e)
            {
                flag = true;
            }
        }
        if(flag)
            return redirect("search", "删除失败");
        else
            return redirect("search", "info.remove.success");
    }
    
	//要改名字
	@Override
	protected String getEntityName() {
		return FloatingBar.class.getName();
	}

}
