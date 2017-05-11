package org.beangle.wenjuan.service;

import java.util.List;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.wenjuan.model.KsapParent;
import org.beangle.wenjuan.model.ZxksParent;


public interface ZxksService {

	public ZxksParent createZxks(Class<?> zxksClass, LongIdObject ksap, User user);

	public List<?> findKsap(Class<?> ksapClass, Class<?> zxksClass, Long userId);

}
