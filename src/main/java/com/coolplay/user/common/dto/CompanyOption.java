package com.coolplay.user.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by majiancheng on 2019/11/26.
 */
public class CompanyOption implements Serializable {

    private static final long serialVersionUID = -6116273478446319902L;

    private Integer id;//俱乐部ID

    private String companyName;//俱乐部名称

    private List<BaseOption> baseOptions;//基地集合

    public CompanyOption() {
    }

    public CompanyOption(Integer id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<BaseOption> getBaseOptions() {
        return baseOptions;
    }

    public void setBaseOptions(List<BaseOption> baseOptions) {
        this.baseOptions = baseOptions;
    }
}
