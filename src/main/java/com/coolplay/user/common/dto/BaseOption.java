package com.coolplay.user.common.dto;

import java.io.Serializable;

/**
 * Created by majiancheng on 2019/11/26.
 */
public class BaseOption implements Serializable {

    private static final long serialVersionUID = 1039350445919260016L;

    private Integer id;//基地ID

    private String baseName;//基地名称

    public BaseOption() {}

    public BaseOption(Integer id, String baseName) {
        this.id = id;
        this.baseName = baseName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }
}
