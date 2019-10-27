package com.coolplay.user.user.api.base;

import com.coolplay.user.common.utils.DistanceUtil;
import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.user.model.CompanyModel;
import com.coolplay.user.user.model.CoolplayBaseModel;
import com.coolplay.user.user.model.LabelModel;
import com.coolplay.user.user.model.UserCollectModel;
import com.coolplay.user.user.service.ICompanyService;
import com.coolplay.user.user.service.ICoolplayBaseService;
import com.coolplay.user.user.service.ILabelService;
import com.coolplay.user.user.service.IUserCollectService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/10/25.
 */
@Controller
@RequestMapping("/api/base/coolplayBase")
public class CoolplayBaseController {

    @Autowired
    private ICoolplayBaseService coolplayBaseService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IUserCollectService userCollectService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map list(@RequestBody CoolplayBaseModel coolplayBaseModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {

        coolplayBaseModel.setIsClose(0);
        coolplayBaseModel.setIsDel(0);
        PageInfo<CoolplayBaseModel> pageInfo = coolplayBaseService
                .selectByFilterAndPage(coolplayBaseModel, pageNum, pageSize);
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            List<Integer> companyIds = new ArrayList<Integer>();
            List<Integer> baseIds = new ArrayList<Integer>();
            for (CoolplayBaseModel tmpCoolplayBase : pageInfo.getList()) {
                if (!companyIds.contains(tmpCoolplayBase.getCompanyId())) {
                    companyIds.add(tmpCoolplayBase.getCompanyId());
                }

                baseIds.add(tmpCoolplayBase.getId());
            }

            Map<Integer, CompanyModel> companyMap = companyService.findMapByCompanyIds(companyIds);
            Map<Integer, List<LabelModel>> labelMap = labelService.findMapByBaseIds(baseIds);

            for (CoolplayBaseModel tmpCoolplayBase : pageInfo.getList()) {
                CompanyModel companyModel = companyMap.get(tmpCoolplayBase.getCompanyId());
                if (companyModel != null) {
                    tmpCoolplayBase.setCompanyName(companyModel.getCompanyName());
                }

                tmpCoolplayBase.setLabelList(labelMap.get(tmpCoolplayBase.getId()));
                tmpCoolplayBase.setDistinct(DistanceUtil
                        .getDistance(coolplayBaseModel.getPosX(), coolplayBaseModel.getPosY(),
                                tmpCoolplayBase.getPosX(), tmpCoolplayBase.getPosY()));
            }
        }

        return PageConvertUtil.grid(pageInfo);
    }

    /**
     * 基地详情
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result detail(@RequestParam("id")Integer id) {
        CoolplayBaseModel coolplayBaseModel = coolplayBaseService.findById(id);

        CompanyModel companyModel = companyService.findCompanyById(coolplayBaseModel.getCompanyId());
        if(companyModel != null) {
            coolplayBaseModel.setCompanyName(companyModel.getCompanyName());
        }

        Map<Integer, List<LabelModel>> labelMap = labelService.findMapByBaseIds(Collections.singletonList(id));
        coolplayBaseModel.setLabelList(labelMap.get(coolplayBaseModel.getId()));

        return ResponseUtil.success(coolplayBaseModel);
    }


    @ResponseBody
    @RequestMapping(value = "/collectBase", method = RequestMethod.POST)
    public Result collectBase(@RequestParam("id")Integer id, @RequestParam("type")Integer type) {
        Integer currUserId = SecurityUtil.getCurrentUserId();

        UserCollectModel userCollectModel = new UserCollectModel();
        userCollectModel.setCollectType(4);
        userCollectModel.setCollectTypeId(id);
        userCollectModel.setUserId(currUserId);
        userCollectModel.setIsDel(0);

        //操作类型 1: 收藏 2:取消收藏

        if(type == 1) {
            if(CollectionUtils.isEmpty(userCollectService.selectByFilter(userCollectModel))) {
                int saveCnt = userCollectService.saveNotNull(userCollectModel);
            }
        } else if(type == 2) {
            int delCnt = userCollectService.delByUserIdAndCollectTypeInfo(currUserId, 4, id);
        }

        int collectCnt = userCollectService.findCntByCollectTypeAndCollectTypeId(4, id);

        return ResponseUtil.success(Collections.singletonMap("collectCnt", collectCnt));
    }
}
