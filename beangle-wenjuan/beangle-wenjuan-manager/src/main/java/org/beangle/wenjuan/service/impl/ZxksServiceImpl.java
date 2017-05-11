package org.beangle.wenjuan.service.impl;

import java.util.Date;
import java.util.List;

import org.beangle.ems.security.User;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.wenjuan.model.KsapParent;
import org.beangle.wenjuan.model.Zxks;
import org.beangle.wenjuan.model.ZxksParent;
import org.beangle.wenjuan.service.ZxksService;

public class ZxksServiceImpl implements ZxksService {
	
	private EntityDao entityDao;
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public ZxksParent getZxks(Class<?> zxksClass, KsapParent ksap, User user) {
		return createZxks(zxksClass, ksap, user);
	}

	private ZxksParent queryZxks(Class<?> zxksClass, LongIdObject ksap, User user) {
		@SuppressWarnings("unchecked")
		OqlBuilder<ZxksParent> query = (OqlBuilder<ZxksParent>) OqlBuilder.from(zxksClass, "zxks");
		query.where("zxks.user.id = :userId", user.getId());
		if(ksap instanceof KsapParent){
			query.where("zxks.ksap.id = :ksapid", ksap.getId());
		}else{
			query.where("zxks.ksapid = :ksapid", ksap.getId());
		}
		List<ZxksParent> zxkss = entityDao.search(query);
		ZxksParent zxks = null;
		if(!zxkss.isEmpty()){
			zxks = zxkss.get(0);
		}
		if(zxkss.size() > 1){
			for(int i = 1; i < zxkss.size(); i++){
				entityDao.remove(zxkss.get(i));
			}
		}
		return zxks;
	}

	public ZxksParent createZxks(Class<?> zxksClass, LongIdObject ksap, User user) {
		ZxksParent zxks = queryZxks(zxksClass, ksap, user);
		if(zxks == null){
			try {
				zxks = (ZxksParent) zxksClass.newInstance();
			} catch (Exception e) {
			}
			if(ksap instanceof KsapParent){
				zxks.setKsap((KsapParent)ksap);
			}else{
				zxks.setKsapid(ksap.getId());
			}
			zxks.setUser(user);
			entityDao.saveOrUpdate(zxks);
		}
		return zxks;
	}

	public List<?> findKsap(Class<?> ksapClass, Class<?> zxksClass, Long userId) {
		@SuppressWarnings("unchecked")
		OqlBuilder<KsapParent> query = (OqlBuilder<KsapParent>) OqlBuilder.from(ksapClass, "ksap");
		query.select("distinct ksap");
		Date current = new Date();
		query.where("ksap.startTime<=:kssj", current);
		query.where("ksap.endTime>=:jssj", current);
		query.where("ksap.enabled=true");
		//添加条件，过滤已交卷在线考试
		query.where("ksap.id not in (select zxks.ksap.id from "+zxksClass.getName()+" zxks where zxks.user.id = :userId and zxks.finished = true)", userId);
		//查询考试学生
//		query.join("left","ksap.users", "user");
//		query.where("user.id = :userId", userId);
		return entityDao.search(query);
	}
	
}
