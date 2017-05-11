/*
 *
 * Copyright c 2005-2009.
 * 
 * Licensed under GNU  LESSER General Public License, Version 3.  
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-8-16            Created
 *  
 ********************************************************************************/
package org.beangle.website.cms.template.widgets;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beangle.commons.collection.page.Limit;

/**
 * 查询分页限制
 * 
 * @author chaostone
 * 
 */
public class PageLimit extends org.beangle.commons.collection.page.PageLimit {

    private int pageNo;
    private int pageSize;
    private int total;

    public PageLimit() {
        super();
    }

    public PageLimit(final int pageNo, final int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;
    }

    public boolean isValid() {
        return pageNo > 0 && pageSize > 0;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("pageNo", this.pageNo).append(
                "pageSize", this.pageSize).toString();
    }

    public final int getFirstPageNo() {
        return 1;
    }

    public final int getMaxPageNo() {
        if (getTotal() < getPageSize()) {
            return 1;
        } else {
            final int remainder = getTotal() % getPageSize();
            final int quotient = getTotal() / getPageSize();
            return (0 == remainder) ? quotient : (quotient + 1);
        }
    }

    public final int getNextPageNo() {
        if (getPageNo() == getMaxPageNo()) {
            return getMaxPageNo();
        } else {
            return getPageNo() + 1;
        }
    }

    public final int getPreviousPageNo() {
        if (getPageNo() == 1) {
            return getPageNo();
        } else {
            return getPageNo() - 1;
        }
    }

    public final int getTotal() {
        return total;
    }

    public void setTotal(final int total) {
        this.total = total;
    }
}
