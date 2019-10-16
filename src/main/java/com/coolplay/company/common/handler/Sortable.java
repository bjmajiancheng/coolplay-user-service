package com.coolplay.company.common.handler;

import com.coolplay.company.common.utils.CamelUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by majiancheng on 2019/9/15.
 */
public class Sortable implements Serializable {

    private static final long serialVersionUID = 9213896339757763626L;

    @Transient
    @JsonIgnore
    private String sort_;

    @Transient
    @JsonIgnore
    private Integer pageOffset;

    @Transient
    @JsonIgnore
    private Integer pageSize;

    public String getSort_() {
        if (StringUtils.isNotEmpty(getSortWithOutOrderBy())) return "order by " + getSortWithOutOrderBy();
        return "";
    }

    public void setSort_(String sort_) {
        this.sort_ = sort_;
    }

    public String getSortWithOutOrderBy() {
        String sortStr = "";
        if (!StringUtils.isEmpty(sort_)) {
            if (sort_.indexOf("_desc") != -1) {
                String filed = CamelUtil.camelToUnderline(sort_.replace("_desc", ""));
                sortStr = "`" + filed + "`" + " desc";
            } else if (sort_.indexOf("_asc") != -1) {
                String filed = CamelUtil.camelToUnderline(sort_.replace("_asc", ""));
                sortStr = "`" + filed + "`" + " asc";
            }
        }
        return sortStr;
    }

    public Integer getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(Integer pageOffset) {
        this.pageOffset = pageOffset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
