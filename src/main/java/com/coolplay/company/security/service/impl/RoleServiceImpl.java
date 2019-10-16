package com.coolplay.company.security.service.impl;

import com.coolplay.company.common.baseservice.impl.BaseService;
import com.coolplay.company.company.model.CompanyDeptModel;
import com.coolplay.company.core.dao.RoleFunctionMapper;
import com.coolplay.company.core.dao.RoleMapper;
import com.coolplay.company.core.dao.UserRoleMapper;
import com.coolplay.company.core.model.RoleFunctionModel;
import com.coolplay.company.core.model.RoleModel;
import com.coolplay.company.core.model.UserRoleModel;
import com.coolplay.company.security.service.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by majiancheng on 2019/9/16.
 */
@Service("roleService")
public class RoleServiceImpl extends BaseService<RoleModel> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleFunctionMapper roleFunctionMapper;

    @Override
    public List<Map> findRoleMatchUpFunctions() {
        return roleMapper.findRoleMatchUpFunctions();
    }

    @Override
    public PageInfo<RoleModel> selectByFilterAndPage(RoleModel roleModel, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RoleModel> list = this.selectByFilter(roleModel);
        return new PageInfo<>(list);
    }

    @Override
    public List<RoleModel> selectByFilter(RoleModel roleModel) {
        Example example = new Example(RoleModel.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(roleModel.getRoleName())) {
            criteria.andLike("roleName", "%" + roleModel.getRoleName() + "%");
        }

        if (roleModel.getCompanyId() != null) {
            criteria.andEqualTo("companyId", roleModel.getCompanyId());
        }

        if (roleModel.getStatus() != null) {
            criteria.andEqualTo("status", roleModel.getStatus());
        }

        if (StringUtils.isNotEmpty(roleModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(roleModel.getSortWithOutOrderBy());
        }
        return getMapper().selectByExample(example);
    }

    @Override
    public RoleModel selectById(int id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Map<Integer, List<UserRoleModel>> findUserRoles(List<Integer> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyMap();
        }

        List<UserRoleModel> userRoleModels = userRoleMapper.findUserRoleByUserIds(userIds);
        if (CollectionUtils.isEmpty(userRoleModels)) {
            return Collections.emptyMap();
        }

        Map<Integer, List<UserRoleModel>> roleModelMap = new HashMap<Integer, List<UserRoleModel>>();
        for (UserRoleModel userRoleModel : userRoleModels) {
            List<UserRoleModel> tmpRoleModels = roleModelMap.get(userRoleModel.getUserId());
            if (CollectionUtils.isEmpty(tmpRoleModels)) {
                tmpRoleModels = new ArrayList<UserRoleModel>();
            }

            tmpRoleModels.add(userRoleModel);
            roleModelMap.put(userRoleModel.getUserId(), tmpRoleModels);
        }

        return roleModelMap;
    }

    @Override
    public List<Integer> getUserIdsByRoleId(int roleId) {
        return userRoleMapper.getUserIdsByRoleId(roleId);
    }

    @Override
    public List<RoleFunctionModel> getRoleFunctionByRoleId(int roleId) {
        return roleFunctionMapper.find(Collections.singletonMap("roleId", roleId));
    }

    @Override
    public List<RoleModel> find(Map<String, Object> param) {
        return roleMapper.find(param);
    }
}
