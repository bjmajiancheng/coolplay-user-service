package com.coolplay.company.security.service;

import com.coolplay.company.common.baseservice.IBaseService;
import com.coolplay.company.common.utils.TreeNode;
import com.coolplay.company.core.model.FunctionModel;
import com.coolplay.company.core.model.RoleModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by majiancheng on 2019/9/18.
 */
public interface IFunctionService extends IBaseService<FunctionModel> {

    public PageInfo<FunctionModel> selectByFilterAndPage(FunctionModel functionModel, int pageNum, int pageSize);

    public List<FunctionModel> selectByFilter(FunctionModel functionModel);

    public FunctionModel selectById(int id);

    List<TreeNode> getFunctionTreeNodes(FunctionModel functionModel);
}
