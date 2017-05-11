package org.beangle.website.cms.action;

import java.util.Date;

import org.beangle.ems.security.User;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.Disposition;
import org.beangle.website.cms.model.OnlineMess;


public class OnlineMessAction extends ContentCommonAction{
	
	@Override
	protected String getEntityName() {
		return OnlineMess.class.getName();
	}
	
	public String index(){
		return forward(new Action(this, "search"));
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		Long onlineMessTypeId = getLong("onlineMessTypeId");
		if(onlineMessTypeId == null){
			onlineMessTypeId = getLong("onlineMessType.id");
		}
		OqlBuilder<OnlineMess> oql = OqlBuilder.from(OnlineMess.class,"onlineMess");
		populateConditions(oql);
		oql.where("onlineMess.onlineMessType.id=:onlineMessTypeId",onlineMessTypeId);
		oql.orderBy(getOrderString("onlineMess.time desc"));
		oql.limit(getPageLimit());
		put("onlineMessTypeId",onlineMessTypeId);
		return oql;
	}
	
	/**
	 * 编辑留言
	 */
	public String edit() {
		OnlineMess online = (OnlineMess) getEntity(OnlineMess.class,"onlineMess");
		if(online.isBack()){
			return redirect("search", "已回复的留言不可处理！","onlineMessTypeId="+online.getOnlineMessType().getId());
		}
		put("backname",getCurrentUser().getFullname());//获取当前用户名称
		OqlBuilder<User> query = OqlBuilder.from(User.class,"user");
		query.where("user.enabled=1");
		query.orderBy("user.fullname");
		put("users",entityDao.search(query));
		OqlBuilder<Disposition> dquery = OqlBuilder.from(Disposition.class,"d");
		dquery.where("d.onlineMess.id=:mid",online.getId());
		dquery.orderBy("d.time desc");
		put("clryjs",entityDao.search(dquery));
		Long onlineMessTypeId = getLong("onlineMessTypeId");
		put("onlineMessTypeId",onlineMessTypeId);
		put("onlineMess",online);
		return forward();
	}
	/**
	 * 保存留言
	 */
	public String save() {
		OnlineMess online = (OnlineMess) populateEntity(OnlineMess.class, "onlineMess");
		entityDao.saveOrUpdate(online);
		Long clr = getLong("clr");
		if(clr != null){
			online.setBack(false);
			Disposition dis = new Disposition();
			dis.setOnlineMess(online);
			dis.setUser((User) entityDao.get(User.class, clr));
			dis.setState(false);
			entityDao.saveOrUpdate(dis);
		}else{
			online.setBack(true);
			online.setBackPerson(getCurrentUser().getFullname());
			online.setBackTime(new Date());
		}
		entityDao.saveOrUpdate(online);
		return redirect("search", "info.save.success","onlineMessTypeId="+online.getOnlineMessType().getId());
	}
	/**
	 * 删除留言
	 * @return
	 */
	public String remove(){
		Long[] onlineIds = getEntityIds();
		Long onlineTypeMessId = null;
		boolean hasError = false;
		boolean hasSuccess = false;
		for(int i=0;i<onlineIds.length;i++){
			OnlineMess online = (OnlineMess) entityDao.get(OnlineMess.class, onlineIds[i]);
			if(onlineTypeMessId == null){
				onlineTypeMessId = online.getOnlineMessType().getId();
			}
			try {
//				OqlBuilder<Disposition> oql = OqlBuilder.from(Disposition.class,"d");
//				oql.where("d.onlineMess.id=:onlineMessId",online.getId());
//				entityDao.remove(entityDao.search(oql));
				entityDao.remove(online);
				hasSuccess = true;
			} catch (Exception e) {
				hasError = true;
			}
		}
		
		String msg = "";
		
		if(hasSuccess && hasError){
			msg = "删除成功，但已使用的记录未做处理！";
		}else if(!hasSuccess && hasError){
			msg = "已使用的记录不可删除！";
		}else{
			msg = "info.delete.success";
		}
		
		return redirect("search", msg,"onlineMessTypeId="+onlineTypeMessId);
	}
	
	public String fenfa(){
		OnlineMess online = (OnlineMess) populateEntity(OnlineMess.class, "onlineMess");
		Long clr = getLong("clr");
		if(clr != null){
			online.setBack(false);
			Disposition dis = new Disposition();
			dis.setOnlineMess(online);
			dis.setUser(getCurrentUser());
			dis.setState(false);
			entityDao.saveOrUpdate(dis);
		}else{
			online.setBack(true);
			online.setBackPerson(getCurrentUser().getFullname());
			online.setBackTime(new Date());
		}
		entityDao.saveOrUpdate(online);
		return redirect("search", "操作成功","onlineMessTypeId="+online.getOnlineMessType().getId());
	}
	
}
