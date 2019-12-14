package com.coolplay.user.common.task;

import com.alibaba.fastjson.JSON;
import com.coolplay.user.common.utils.DateStyle;
import com.coolplay.user.common.utils.DateUtil;
import com.coolplay.user.common.utils.SpringContextUtil;
import com.coolplay.user.user.dto.EsBaseDto;
import com.coolplay.user.user.model.CompanyModel;
import com.coolplay.user.user.model.CoolplayBaseLabelModel;
import com.coolplay.user.user.model.CoolplayBaseModel;
import com.coolplay.user.user.service.ICompanyService;
import com.coolplay.user.user.service.ICoolplayBaseLabelService;
import com.coolplay.user.user.service.ICoolplayBaseService;
import com.wutuobang.search.bean.EsBaseBean;
import com.wutuobang.search.service.IIndexSaveService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by majiancheng on 2019/12/14.
 */
public class EsDataTask {

    private static final Logger logger = LoggerFactory.getLogger(EsDataTask.class);

    private ICoolplayBaseService coolplayBaseService = (ICoolplayBaseService) SpringContextUtil
            .getBean("coolplayBaseService");

    private ICompanyService companyService = (ICompanyService) SpringContextUtil.getBean("companyService");

    private ICoolplayBaseLabelService coolplayBaseLabelService = (ICoolplayBaseLabelService) SpringContextUtil.getBean("coolplayBaseLabelService");

    private IIndexSaveService indexSaveService = (IIndexSaveService) SpringContextUtil.getBean("indexSaveService");

    /**
     * 同步es索引数据
     */
    public void syncEsData() {
        try {
            String preMinuteStr = DateUtil.DateToString(DateUtil.addMinute(new Date(), -2), DateStyle.YYYY_MM_DD_HH_MM_SS);

            List<CompanyModel> companyModels = companyService.findByLastUpdatetime(preMinuteStr);
            List<CoolplayBaseModel> coolplayBaseModels = coolplayBaseService.findByLastUpdatetime(preMinuteStr);

            List<EsBaseBean> esBaseBeans = new ArrayList<EsBaseBean>();
            if(CollectionUtils.isNotEmpty(companyModels)) {
                for(CompanyModel companyModel : companyModels) {
                    Map<String, Object> location = new HashMap<String, Object>();
                    location.put("lon", companyModel.getPosX());
                    location.put("lat", companyModel.getPosY());

                    EsBaseBean esBaseBean = new EsBaseBean();
                    esBaseBean.setId(String.format("company_%d", companyModel.getId()));
                    esBaseBean.setUserId(0);
                    esBaseBean.setCtimeStamp((int)(companyModel.getCtime().getTime() / 1000));
                    esBaseBean.setLabelNames(Collections.emptyList());
                    esBaseBean.setLocation(location);
                    esBaseBean.setName(companyModel.getCompanyName());
                    esBaseBean.setType(1);
                    esBaseBean.setTypeId(companyModel.getId());
                    esBaseBeans.add(esBaseBean);
                }
            }

            if(CollectionUtils.isNotEmpty(coolplayBaseModels)) {
                List<Integer> baseIds = new ArrayList<Integer>();

                for(CoolplayBaseModel baseModel : coolplayBaseModels) {
                    baseIds.add(baseModel.getId());
                }

                Map<Integer, List<CoolplayBaseLabelModel>> baseLabelMap = coolplayBaseLabelService.findMapByBaseIds(baseIds);

                for(CoolplayBaseModel baseModel : coolplayBaseModels) {
                    Map<String, Object> location = new HashMap<String, Object>();
                    location.put("lon", baseModel.getPosX());
                    location.put("lat", baseModel.getPosY());

                    List<CoolplayBaseLabelModel> baseLabelModels = baseLabelMap.get(baseModel.getId());
                    List<String> labelNames = new ArrayList<String>();
                    if(CollectionUtils.isNotEmpty(baseLabelModels)) {
                        for(CoolplayBaseLabelModel baseLabelModel : baseLabelModels) {
                            labelNames.add(baseLabelModel.getLabelName());
                        }
                    }

                    EsBaseBean esBaseBean = new EsBaseBean();
                    esBaseBean.setId(String.format("base_%d", baseModel.getId()));
                    esBaseBean.setUserId(baseModel.getCompanyUserId());
                    esBaseBean.setCtimeStamp((int)(baseModel.getCtime().getTime() / 1000));
                    esBaseBean.setLabelNames(labelNames);
                    esBaseBean.setLocation(location);
                    esBaseBean.setName(baseModel.getBaseName());
                    esBaseBean.setType(2);
                    esBaseBean.setTypeId(baseModel.getId());
                    esBaseBeans.add(esBaseBean);
                }
            }

            if(CollectionUtils.isNotEmpty(esBaseBeans)) {
                for(EsBaseBean esBaseBean : esBaseBeans) {
                    indexSaveService.upsertIndexData("es_base", esBaseBean.getId(), JSON.toJSONString(esBaseBean));
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("同步ES索引数据异常, e:{}.", e.getMessage());
        }

    }
}
