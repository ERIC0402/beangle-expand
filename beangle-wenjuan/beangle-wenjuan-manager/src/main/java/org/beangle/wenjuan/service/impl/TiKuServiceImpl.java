package org.beangle.wenjuan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.ems.security.Group;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.wenjuan.model.TiKu;
import org.beangle.wenjuan.model.TiMu;
import org.beangle.wenjuan.service.TiKuService;

public class TiKuServiceImpl extends BaseServiceImpl implements TiKuService {

	/**
	 * 查询有效库题
	 * @return
	 */
	public List<TiKu> findTiKu() {
		OqlBuilder<TiKu> query = OqlBuilder.from(TiKu.class, "tk");
		query.where("tk.enabled = 1");
		return  entityDao.search(query);
	}

	public Map<String, TiKu> getTiKuMap() {
		List<TiKu> list = entityDao.getAll(TiKu.class);
		Map<String, TiKu> map = new HashMap<String, TiKu>(list.size());
		for(TiKu tiKu : list){
			map.put(tiKu.getTkmc(), tiKu);
		}
		return map;
	}

	public List<TiKu> findYQXTiKu(boolean isAdmin, Set<Group> groups, Long userid) {
		OqlBuilder<TiKu> query = OqlBuilder.from(TiKu.class,"t");
		//判断是否是管理员，如果是管理员则不判断角色权限，否侧进行角色判断
		if(!isAdmin){
			if(groups  != null && groups.size() > 0){
				query.join("left outer", "t.groups", "g");
				query.join("left outer", "t.users", "u");
				query.where("g in (:gs) or t.createUser.id = :userid or u.id=:userid", groups, userid);
			}
		}
		query.select("select distinct t");
		return entityDao.search(query);
	}

	public void updateTmsl() {
		 entityDao.executeUpdateHql("update " + TiKu.class.getName() + " tk set tk.tmsl = (select count(*) from "+TiMu.class.getName()+" tm where tm.sstk.id = tk.id)");
	}
	
}
