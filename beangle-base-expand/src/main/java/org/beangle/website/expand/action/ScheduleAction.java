package org.beangle.website.expand.action;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.expand.model.Schedule;
import org.beangle.website.expand.utils.DictTypeUtils;


public class ScheduleAction extends CommonAction {

	public String index() {
		putDictDatas();
		return forward();
	}

	
	/**
	 * 传值
	 */
	protected void putDictDatas(){
		put("types",getDictDataByDictType(DictTypeUtils.TYPE_RCAP)); //日程安排类型
	}
	
	/**
	 * 查询语句
	 */
	protected OqlBuilder<Schedule> getQueryBuilder() {
		OqlBuilder<Schedule> oql = OqlBuilder.from(Schedule.class,"schedule");
		populateConditions(oql);
		String order = get(Order.ORDER_STR);
		if(StringUtils.isEmpty(order)){
			order = "schedule.type desc,schedule.kssj asc";
		}
		oql.orderBy(order);
		oql.limit(getPageLimit());
		return oql;
	}

	/**
	 * 编辑
	 */
	protected void editSetting(Entity<?> entity) {
		Schedule schedule = (Schedule) entity;
		putDictDatas();
		put("schedule",schedule);
		
	}
	
	/**
	 * 保存
	 */
	protected String saveAndForward(Entity<?> entity) {
		Schedule schedule = (Schedule) entity;
	    entityDao.saveOrUpdate(schedule);
		return redirect("search", "info.save.success");
	}
	
	/**
	 * 删除
	 */
    public String remove()
    {
        Long scheduleIds[] = getEntityIds();
        boolean flag = false;
        for(int i = 0; i < scheduleIds.length; i++)
        {
        	Schedule schedules = (Schedule)entityDao.get(Schedule.class, scheduleIds[i]);
            try
            {
                entityDao.remove(schedules);
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
		return Schedule.class.getName();
	}

}
