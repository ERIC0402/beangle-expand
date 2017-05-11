package org.beangle.wenjuan.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.wenjuan.model.TiKu;
import org.beangle.wenjuan.model.TiMu;
/**
 * 题库管理类
 * @author GZW
 *
 */
public class TiKuAction extends WenJuanCommonAction {

	@Override
	protected String getEntityName() {
		return TiKu.class.getName();
	}
	
	@Override
	protected void indexSetting() {
		//传递分类
		put("types",getDictTrees("TMFL"));
	}
	
	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<TiKu> query = OqlBuilder.from(TiKu.class,getShortName());
		//判断是否是管理员，如果是管理员则不判断角色权限，否侧进行角色判断
		if(!isAdmin()){
			Set<Group> groups = getUserGroups(getCurrentUser());
			if(groups != null && groups.size() > 0){
				query.join("left outer", getShortName() + ".groups", "g");
				query.join("left outer", getShortName() + ".users", "u");
				query.where("g in (:gs) or tiKu.createUser.id = :userid or u.id=:userid",groups, getUserId());
			}
		}
		populateConditions(query);
		query.select("distinct "+getShortName());
		query.orderBy(getOrderString());
		query.limit(getPageLimit());
		return query;
	}
	
	/**
	 * 往页面传递数据
	 */
	protected void putDatas(){
		//传递角色
		put("groups",entityDao.getAll(Group.class));
		//传递分类
		put("types",getDictTrees("TMFL"));
	}
	
	@Override
	protected void editSetting(Entity<?> entity) {
		putDatas();
		super.editSetting(entity);
	}
	
	@Override
	protected String saveAndForward(Entity<?> entity) {
		TiKu tiKu = (TiKu) entity;
		if(tiKu.getCreateUser() == null){
			tiKu.setCreateUser(getCurrentUser());
		}
		//判断是否存在同名题库，如果存在则跳转到编辑页面并进行提示
		if(exist(tiKu, "tkmc", tiKu.getTkmc())){
			put(getShortName(),tiKu);
			putDatas();
			return forward("form","该题库名称已存在，请重新命名");
		}
		//获取选中角色ID，并组建Long类型数组
		Long[] groupIds = StrUtils.splitToLong(get("groupIds"));
		//查询角色
		List<Group> groups = entityDao.get(Group.class, groupIds);
		//清理原有角色
		tiKu.getGroups().clear();
		//设置角色
		tiKu.getGroups().addAll(groups);
		
		Long[] userIds = StrUtils.splitToLong(get("userIds"));
		//查询角色
		List<User> users = entityDao.get(User.class, userIds);
		//清理原有角色
		tiKu.getUsers().clear();
		//设置角色
		tiKu.getUsers().addAll(users);
		entityDao.saveOrUpdate(tiKu);
		return redirect("search","保存成功");
	}
	
	@Override
	protected String removeAndForward(Collection<?> entities) {
		//有删除成功的记录
		boolean hasSuccess = false;
		//有删除失败的记录
		boolean hasFailure = false;
		for (Iterator<?> iterator = entities.iterator(); iterator.hasNext();) {
			TiKu tiKu = (TiKu) iterator.next();
			try {
				Set<TiMu> tiMuList = tiKu.getTms();
				if(tiMuList == null || tiMuList.size() <= 0){
					entityDao.remove(tiKu);
					hasSuccess = true;
				}else{
					hasFailure = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				hasFailure = true;
			}
		}
		//如果有删除成功的记录则判断是否有删除失败的记录，如果有表示两种情况同时存在则提示“删除成功，但已被使用的题库未作处理”，否则没有删除失败的记录则提示“删除成功”
		if(hasSuccess){
			if(hasFailure){
				return redirect("search","删除成功，但已被使用的题库未作处理");
			}else{
				return redirect("search","删除成功");
			}
		}else{//没有删除成功的记录表示全部删除失败，则提示，“删除失败，已被使用的题库不可删除”
			return redirect("search","删除失败，已被使用的题库不可删除");
		}
	}
}
