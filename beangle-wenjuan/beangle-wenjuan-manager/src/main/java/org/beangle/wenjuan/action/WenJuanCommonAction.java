package org.beangle.wenjuan.action;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.beangle.ems.security.Group;
import org.beangle.website.common.action.FileAction;


public class WenJuanCommonAction extends FileAction {
	
	@Override
	protected boolean isAdmin() {
		Group group = entityDao.get(Group.class, 225L);
		Set<Group> groups = getUserGroups();
		if(CollectionUtils.isNotEmpty(groups) && groups.contains(group)){
			return true;
		}
		return false;
	}
}
