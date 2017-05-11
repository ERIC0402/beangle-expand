package org.beangle.website.cms.action;

import java.util.Date;
import java.util.List;

import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.website.cms.action.ContentCommonAction;
import org.beangle.website.cms.model.Link;

import org.beangle.website.common.util.SeqStringUtil;

/**
 * 模块管理
 * @author DONHKO
 *
 */
public class LinkAction extends ContentCommonAction {

	@Override
	protected String getEntityName() {
		return Link.class.getName();
	}
	
    public String index() {
    	return forward(new Action(this, "search"));
    }
    
    @Override
    protected QueryBuilder<?> getQueryBuilder() {
    	Long linksTypeId = getLong("linksType.id");
    	OqlBuilder<Link> oql = OqlBuilder.from(Link.class,"link");
		populateConditions(oql);
		oql.where("link.linkType.id=:linksTypeId",linksTypeId);
		oql.orderBy(getOrderString("link.orders"));
		oql.limit(getPageLimit());
		put("linksTypeId",linksTypeId);
		return oql;
    }
    
    @Override
    protected void editSetting(Entity<?> entity) {
    	Link link = (Link) entity;
    	put("link",link);
    	put("currentDate",new Date());
    	put("linksTypeId",getLong("linksTypeId"));
    }

    public String save() {
        Link link = (Link) populateEntity(Link.class, "link");
        //获取所有的链接(排序后)
        List<Link> linkList = orderBy(getLong("link.linkType.id"));
        String endOrders = "";//记录最后一个orders
        String newOrders = "";//保存当前添加的链接对象orders属性值
        if (linkList != null) {
            if (linkList.isEmpty()) {
                endOrders = "0";
            } else {
                endOrders = ((Link) linkList.get(linkList.size() - 1)).getOrders();
            }
        }
        int endNum = Integer.parseInt(endOrders);
        int newNum = endNum + 1;
        if (newNum < 10) {
            newOrders = "0" + newNum;
        } else {
            newOrders = newNum + "";
        }
        link.setOrders(newOrders);
        entityDao.saveOrUpdate(link);
        saveOperationLog("内容管理-友情链接", "新建了一个链接" + link.getName());
        return redirect("search", "info.save.success","linksType.id="+link.getLinkType().getId());
    }

    public String remove() {
        String linkId = get("link.ids");
        Long[] ids = SeqStringUtil.transformToLong(linkId);
        Long linkTypeId = null;
        for(int i=0;i<ids.length;i++){
        	Link link = (Link) entityDao.get(Link.class, ids[i]);
        	if(linkTypeId == null){
        		linkTypeId = link.getLinkType().getId();
        	}
            entityDao.remove(link);
            saveOperationLog("内容管理-友情链接", "删除了一个链接" + link.getName());
        }
        return redirect("search", "删除成功","linksType.id="+linkTypeId);
    }

    public List<Link> orderBy(Long linksTypeId) {
    	OqlBuilder<Link> query = OqlBuilder.from(Link.class, "link");
        query.where("link.linkType.id =:id",linksTypeId);
        query.orderBy("link.orders");
        List<Link> linkList = entityDao.search(query);
        return linkList;
    }

    //移动处理
    public String up() {
        boolean flag = getBool("flag");
        Long cid = getLong("link.id");
        Link link = entityDao.get(Link.class, cid);
        List<Link> linkList = orderBy(link.getLinkType().getId());
        for (int i = 0; i < linkList.size(); i++) {
            Link link1 = (Link) linkList.get(i);
            if (cid.equals(link1.getId())) {

                if (i == linkList.size() - 1 && flag == false) {
                    return redirect("search", "下移失败","linksType.id="+link1.getLinkType().getId());
                }


                if (i == 0 && flag == true) {
                    return redirect("search", "上移失败","linksType.id="+link1.getLinkType().getId());
                }

                //获取上移对象的orders属性值
                String orders1 = link1.getOrders();
                //获取当前链接对象的上一个对象
                Link link2 = null;
                if (flag == true)//true代表上移,false代表下移
                {
                    link2 = (Link) linkList.get(i - 1);
                } else {
                    link2 = (Link) linkList.get(i + 1);
                }
                String orders2 = link2.getOrders();
                //交换orders属性值
                link1.setOrders(orders2);
                link2.setOrders(orders1);
                entityDao.saveOrUpdate(link1);
                entityDao.saveOrUpdate(link2);

            }
        }
        return redirect("search", "移动成功","linksType.id="+link.getLinkType().getId());
    }
}
