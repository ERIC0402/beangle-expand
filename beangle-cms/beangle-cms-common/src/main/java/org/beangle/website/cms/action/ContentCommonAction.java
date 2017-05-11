package org.beangle.website.cms.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.beangle.ems.security.Group;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.cms.model.Column;
import org.beangle.website.cms.model.Content;
import org.beangle.website.cms.model.ContentExtProperty;
import org.beangle.website.cms.model.ExtProperty;

import org.beangle.website.system.model.DictData;
import org.beangle.website.workflow.model.TaskNode;

public class ContentCommonAction extends CommonAction {
	/**
	 * 当前用户是否有权限操作当前节点
	 * @param node 当前节点
	 * @return 
	 */
	protected boolean isTaskNodeRole(TaskNode node){
		boolean bool = false;
		Set<Group> ds = node.getRoles();
		Set<Group> set = getUserGroups(getCurrentUser());
		for (Iterator<Group> it = set.iterator(); it.hasNext();) {
			Group g = it.next();
			if (ds.contains(g)) {
				bool = true;
				break;
			}
		}
		return bool;
	}
	

	/**
	 * 根据栏目获取扩展属性
	 * @param column 栏目
	 * @return
	 */
	protected Set<ExtProperty> getExtProperties(Column column){
		String code = "";
		if (column.getExtProperty() != null) {
			code = column.getExtProperty().getCode();
		}
		Set<ExtProperty> sets = new HashSet<ExtProperty>();
		if ("EXT_PROPERTY_SOURCE_PARENT".equals(code)) {
			Column parent = column.getColumns();
			if (parent != null) {
				sets = parent.getExtpropertys();
			}
		} else if ("EXT_PROPERTY_SOURCE_SELF".equals(code)) {
			sets = column.getExtpropertys();
		}
		return sets;
	}

	/**
	 * 传送信息扩展属性
	 * 
	 * @param sets
	 *            扩展属性集合
	 */
	protected void putExtProperties(Set<ExtProperty> sets) {
		for (Iterator<ExtProperty> it = sets.iterator(); it.hasNext();) {
			ExtProperty ext = it.next();
			if ("byType".equals(ext.getFieldName())) {
				put("byTypes", getDictDataByDictType(ext.getValue().getId()));
				continue;
			}
			if ("recordType".equals(ext.getFieldName())) {
				put("recordTypes",
						getDictDataByDictType(ext.getValue().getId()));
				continue;
			}
			if ("publicType".equals(ext.getFieldName())) {
				put("publicTypes",
						getDictDataByDictType(ext.getValue().getId()));
				continue;
			}
			// if ("department".equals(ext.getEn_name())) {
			// put("departments", entityDao.load(DictData.class, map));
			// continue;
			// }
		}
	}

	/**
	 * 保存信息扩展属性
	 * 
	 * @param content
	 *            信息
	 * @param column
	 *            栏目
	 * @param sets
	 *            信息扩展属性集合
	 */
	protected void saveExtProperties(Content content, Column column,
			Set<ExtProperty> sets,ContentExtProperty cep) {
		if(cep == null){
			cep = new ContentExtProperty();
		}
		cep.setContent(content);
		for (Iterator<ExtProperty> it = sets.iterator(); it.hasNext();) {
			ExtProperty ext = it.next();
			String value = get(ext.getFieldName());
			if ("save_path".equals(ext.getFieldName())) {
				cep.setSavePath(value);
				continue;
			}
			if ("code".equals(ext.getFieldName())) {
				cep.setCode(value);
				continue;
			}
			if ("school".equals(ext.getFieldName())) {
				cep.setSchool(value);
				continue;
			}

			if ("produceDate".equals(ext.getFieldName())) {
				cep.setProduceDate(getDate("produceDate"));
				continue;
			}

			if ("indent".equals(ext.getFieldName())) {

				StringBuffer indent = new StringBuffer();
				indent.append("G010");
				// System.out.println("多少级"+column.getOrders().length());
				if (column.getOrders().length() == 10) {
					indent.append(column.getOrders().substring(6, 10));
					indent.append("000");
				} else if (column.getOrders().length() == 12) {
					indent.append(column.getOrders().substring(6, 10));
					indent.append("0");
					indent.append(column.getOrders().substring(10, 12));
				}
				indent.append("-");
				Date indentDate = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy");
				String year = format.format(indentDate);
				indent.append(year);
				indent.append("-");
				String hql2 = "select count (c) from org.beangle.website.cms.model.ColumnContent c where c.columns.orders like '0103%'";
				List num = entityDao.searchHQLQuery(hql2);
				// System.out.println("num==="+num.get(0));
				// lsh = 流水号
				StringBuffer lsh = new StringBuffer();
				String prefix[] = { "", "0", "00", "000" };
				String str = String.valueOf(num.get(0));
				str = prefix[4 - str.length()];
				lsh.append(str);
				lsh.append(String.valueOf(num.get(0)));
				indent.append(lsh.toString());
				put("indent", indent);
				cep.setIndent(indent.toString());

				// cep.setIndent(value);
				continue;
			}

			// 载体类型
			if ("byType".equals(ext.getFieldName())) {
				if (value != null && value != "") {
					cep.setByType((DictData) entityDao.get(DictData.class,
							Long.parseLong(value)));
				}
				continue;
			}
			// 记录形式
			if ("recordType".equals(ext.getFieldName())) {
				if (value != null && value != "") {
					cep.setRecordType((DictData) entityDao.get(DictData.class,
							Long.parseLong(value)));
				}
				continue;
			}

			// 公开类型
			if ("publicType".equals(ext.getFieldName())) {
				if (value != null && value != "") {
					cep.setPublicType((DictData) entityDao.get(DictData.class,
							Long.parseLong(value)));
				}
				continue;
			}
			// if ("department".equals(ext.getEn_name())) {
			// if (value != null && value != "") {
			// cep.setDepartment((DictData) entityDao.get(DictData.class,
			// Long.parseLong(value)));
			// }
			// continue;
			// }
		}

		entityDao.saveOrUpdate(cep);
	}
	
	/**
	 * 根据信息获取信息扩展属性
	 * @param content
	 * @return
	 */
	protected ContentExtProperty getContentExtProperty(Content content){
		OqlBuilder<ContentExtProperty> oql = OqlBuilder.from(ContentExtProperty.class,"c");
		oql.where("c.content.id=:cotnentId",content.getId());
		List<ContentExtProperty> ceps = entityDao.search(oql);
		ContentExtProperty cep = new ContentExtProperty();
		if(ceps != null && ceps.size() > 0){
			cep = ceps.get(0);
		}
		return cep;
	}
}
