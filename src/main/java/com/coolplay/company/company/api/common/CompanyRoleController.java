package com.coolplay.company.company.api.common;

import com.coolplay.company.common.utils.PageConvertUtil;
import com.coolplay.company.common.utils.ResponseUtil;
import com.coolplay.company.common.utils.Result;
import com.coolplay.company.company.model.CompanyDeptModel;
import com.coolplay.company.company.service.ICompanyRoleFunctionService;
import com.coolplay.company.core.model.RoleFunctionModel;
import com.coolplay.company.core.model.RoleModel;
import com.coolplay.company.security.security.CoolplayUserCache;
import com.coolplay.company.security.service.IRoleService;
import com.coolplay.company.security.utils.SecurityUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/19.
 */
@Controller
@RequestMapping("/api/common/companyRole")
public class CompanyRoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private CoolplayUserCache coolplayUserCache;

    @Autowired
    private ICompanyRoleFunctionService companyRoleFunctionService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(RoleModel roleModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        roleModel.setStatus(1);
        roleModel.setCompanyId(SecurityUtil.getCurrentCompanyId());
        PageInfo<RoleModel> pageInfo = roleService.selectByFilterAndPage(roleModel, pageNum, pageSize);

        return PageConvertUtil.grid(pageInfo);
    }

    /**
     * 获取公司角色信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCompanyRole", method = RequestMethod.GET)
    public Result getCompanyRole(@RequestParam("id") int id) {
        RoleModel roleModel = roleService.selectById(id);

        List<RoleFunctionModel> roleFunctions = companyRoleFunctionService.find(Collections.singletonMap("roleId", id));
        if (CollectionUtils.isNotEmpty(roleFunctions)) {
            List<Integer> functionIds = new ArrayList<Integer>(roleFunctions.size());
            for (RoleFunctionModel roleFunction : roleFunctions) {
                functionIds.add(roleFunction.getFunctionId());
            }

            roleModel.setFunctionIds(functionIds);
        }

        return ResponseUtil.success(roleModel);
    }

    /**
     * 添加公司角色信息
     *
     * @param roleModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveCompanyRole", method = RequestMethod.POST)
    public Result saveCompanyRole(RoleModel roleModel) {
        roleModel.setCompanyId(SecurityUtil.getCurrentCompanyId());
        roleModel.setStatus(1);
        int addCnt = roleService.save(roleModel);

        if (CollectionUtils.isNotEmpty(roleModel.getFunctionIds())) {
            for (Integer functionId : roleModel.getFunctionIds()) {
                RoleFunctionModel roleFunctionModel = new RoleFunctionModel();
                roleFunctionModel.setRoleId(roleModel.getId());
                roleFunctionModel.setFunctionId(functionId);
                companyRoleFunctionService.saveNotNull(roleFunctionModel);
            }
        }

        return ResponseUtil.success();
    }

    /**
     * 添加公司角色信息
     *
     * @param roleModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateCompanyRole", method = RequestMethod.POST)
    public Result updateCompanyRole(RoleModel roleModel) {
        int addCnt = roleService.updateNotNull(roleModel);

        int delCnt = companyRoleFunctionService.delByRoleId(roleModel.getId());

        if (CollectionUtils.isNotEmpty(roleModel.getFunctionIds())) {
            for (Integer functionId : roleModel.getFunctionIds()) {
                RoleFunctionModel roleFunctionModel = new RoleFunctionModel();
                roleFunctionModel.setRoleId(roleModel.getId());
                roleFunctionModel.setFunctionId(functionId);
                companyRoleFunctionService.saveNotNull(roleFunctionModel);
            }
        }

        return ResponseUtil.success();
    }

    /**
     * 禁用或启用公司角色
     *
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delCompanyRole", method = RequestMethod.GET)
    public Result disableCompanyRole(@RequestParam("id") int id, @RequestParam("status") int status) {
        RoleModel roleModel = new RoleModel();
        roleModel.setId(id);
        roleModel.setStatus(status);
        int updateCnt = roleService.updateNotNull(roleModel);
        List<Integer> userIds = roleService.getUserIdsByRoleId(id);

        //清除角色缓存信息
        if (CollectionUtils.isNotEmpty(userIds)) {
            for (Integer userId : userIds) {
                coolplayUserCache.removeUserFromCacheByUserId(userId);
            }
        }

        return ResponseUtil.success();
    }

    /**
     * 获取角色菜单信息
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCompanyRoleFunctions", method = RequestMethod.GET)
    public Result getRoleFunctions(@RequestParam("roleId") int roleId) {
        List<RoleFunctionModel> roleFunctionModels = roleService.getRoleFunctionByRoleId(roleId);

        return ResponseUtil.success(roleFunctionModels);
    }
}
