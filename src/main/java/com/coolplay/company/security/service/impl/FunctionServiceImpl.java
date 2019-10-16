package com.coolplay.company.security.service.impl;

import com.coolplay.company.common.baseservice.impl.BaseService;
import com.coolplay.company.common.utils.TreeNode;
import com.coolplay.company.core.dao.FunctionMapper;
import com.coolplay.company.core.model.FunctionModel;
import com.coolplay.company.core.model.RoleModel;
import com.coolplay.company.security.service.IFunctionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by majiancheng on 2019/9/18.
 */
@Service("functionService")
public class FunctionServiceImpl extends BaseService<FunctionModel> implements IFunctionService {

    @Autowired
    private FunctionMapper functionMapper;

    @Override
    public PageInfo<FunctionModel> selectByFilterAndPage(FunctionModel functionModel, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FunctionModel> list = this.selectByFilter(functionModel);
        return new PageInfo<>(list);
    }

    @Override
    public List<FunctionModel> selectByFilter(FunctionModel functionModel) {
        Example example = new Example(FunctionModel.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(functionModel.getFunctionName())) {
            criteria.andLike("functionName", "%" + functionModel.getFunctionName() + "%");
        }
        if (StringUtils.isNotEmpty(functionModel.getAction())) {
            criteria.andLike("action", "%" + functionModel.getAction() + "%");
        }
        if (StringUtils.isNotEmpty(functionModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(functionModel.getSortWithOutOrderBy());
        }
        return getMapper().selectByExample(example);
    }

    @Override
    public FunctionModel selectById(int id) {
        return functionMapper.selectById(id);
    }

    @Override
    public List<TreeNode> getFunctionTreeNodes(FunctionModel functionModel) {
        return functionMapper.selectFunctionTreeNodes(functionModel);
    }
}
