package com.coolplay.company.company.api.company;

import com.coolplay.company.common.utils.PageConvertUtil;
import com.coolplay.company.common.utils.ResponseUtil;
import com.coolplay.company.common.utils.Result;
import com.coolplay.company.company.model.CompanyCircleModel;
import com.coolplay.company.company.model.CoolplayBaseLabelModel;
import com.coolplay.company.company.model.CoolplayBaseModel;
import com.coolplay.company.company.service.ICoolplayBaseLabelService;
import com.coolplay.company.company.service.ICoolplayBaseService;
import com.coolplay.company.core.model.UserModel;
import com.coolplay.company.core.model.UserRoleModel;
import com.coolplay.company.security.service.IUserService;
import com.coolplay.company.security.utils.SecurityUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by majiancheng on 2019/9/22.
 */
@Controller
@RequestMapping("/api/company/coolplayBase")
public class CoolplayBaseController {

    @Autowired
    private ICoolplayBaseService coolplayBaseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICoolplayBaseLabelService coolplayBaseLabelService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(CoolplayBaseModel coolplayBaseModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {

        coolplayBaseModel.setIsDel(0);
        PageInfo<CoolplayBaseModel> pageInfo = coolplayBaseService.selectByFilterAndPage(coolplayBaseModel, pageNum, pageSize);

        if(CollectionUtils.isNotEmpty(pageInfo.getList())) {
            List<Integer> userIds = new ArrayList<Integer>();
            List<Integer> baseIds = new ArrayList<Integer>();
            for (CoolplayBaseModel tmpCoolplayBase : pageInfo.getList()) {
                userIds.add(tmpCoolplayBase.getCompanyUserId());
                baseIds.add(tmpCoolplayBase.getId());
            }

            Map<Integer, UserModel> userModelMap = userService.findUserMapByUserIds(userIds);
            Map<Integer, List<CoolplayBaseLabelModel>> baseLabelModelMap = coolplayBaseLabelService.findMapByBaseIds(baseIds);
            for(CoolplayBaseModel tmpCoolplayBase : pageInfo.getList()) {
                UserModel userModel = userModelMap.get(tmpCoolplayBase.getCompanyUserId());
                if(userModel != null) {
                    tmpCoolplayBase.setCompanyUserName(userModel.getDisplayName());
                }
                List<CoolplayBaseLabelModel> baseLabelModels = baseLabelModelMap.get(tmpCoolplayBase.getId());
                if(CollectionUtils.isNotEmpty(baseLabelModels)) {
                    StringBuffer sb = new StringBuffer();
                    for(CoolplayBaseLabelModel baseLabelModel : baseLabelModels) {
                        if(sb.length() > 0) {
                            sb.append("、");
                        }

                        sb.append(baseLabelModel.getLabelName());
                    }
                    tmpCoolplayBase.setLabelName(sb.toString());
                }
            }
        }
        return PageConvertUtil.grid(pageInfo);
    }

    @ResponseBody
    @RequestMapping(value="/getCoolplayBase", method = RequestMethod.GET)
    public Result getCoolplayBase(@RequestParam("id") int id) {
        CoolplayBaseModel coolplayBaseModel = coolplayBaseService.findById(id);

        Map<Integer, List<CoolplayBaseLabelModel>> baseLabelModelMap = coolplayBaseLabelService.findMapByBaseIds(
                Collections.singletonList(id));
        if (MapUtils.isNotEmpty(baseLabelModelMap)) {
            List<CoolplayBaseLabelModel> baseLabelModels = baseLabelModelMap.get(id);
            if(CollectionUtils.isNotEmpty(baseLabelModels)) {
                StringBuffer sb = new StringBuffer();
                for(CoolplayBaseLabelModel baseLabelModel : baseLabelModels) {
                    if(sb.length() > 0) {
                        sb.append("、");
                    }
                    sb.append(baseLabelModel.getLabelName());
                }

                coolplayBaseModel.setLabelName(sb.toString());
            }
        }

        return ResponseUtil.success(coolplayBaseModel);
    }

    @ResponseBody
    @RequestMapping(value="/delCoolplayBase", method = RequestMethod.GET)
    public Result delCoolplayBase(@RequestParam("id") int id, @RequestParam("isDel") int isDel) {
        CoolplayBaseModel coolplayBaseModel = new CoolplayBaseModel();
        coolplayBaseModel.setId(id);
        coolplayBaseModel.setIsDel(isDel);

        int updateCnt = coolplayBaseService.updateNotNull(coolplayBaseModel);

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value="/closeCoolplayBase", method = RequestMethod.GET)
    public Result closeCoolplayBase(@RequestParam("id") int id, @RequestParam("isClose") int isClose) {
        CoolplayBaseModel coolplayBaseModel = new CoolplayBaseModel();
        coolplayBaseModel.setId(id);
        coolplayBaseModel.setIsClose(isClose);

        int updateCnt = coolplayBaseService.updateNotNull(coolplayBaseModel);
        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value="/saveCoolplayBase", method = RequestMethod.POST)
    public Result saveCoolplayBase(CoolplayBaseModel coolplayBaseModel) {

        try{
            coolplayBaseModel.setCompanyId(SecurityUtil.getCurrentCompanyId());
            coolplayBaseModel.setCompanyUserId(SecurityUtil.getCurrentUserId());
            coolplayBaseModel.setIsClose(0);
            coolplayBaseModel.setIsDel(0);
            int saveCnt = coolplayBaseService.saveNotNull(coolplayBaseModel);

            if(CollectionUtils.isNotEmpty(coolplayBaseModel.getLabelIds())) {
                for(Integer labelId : coolplayBaseModel.getLabelIds()) {
                    CoolplayBaseLabelModel baseLabelModel = new CoolplayBaseLabelModel();
                    baseLabelModel.setLabelId(labelId);
                    baseLabelModel.setCoolplayBaseId(coolplayBaseModel.getId());
                    baseLabelModel.setCtime(new Date());

                    coolplayBaseLabelService.saveNotNull(baseLabelModel);
                }
            }

        } catch(DuplicateKeyException e) {
            e.printStackTrace();
            return ResponseUtil.error("基地昵称不能重复, 请更换基地昵称!!");
        } catch(Exception e) {
            return ResponseUtil.error("系统异常, 请稍后重试!!");
        }

        return ResponseUtil.success();
    }
}
