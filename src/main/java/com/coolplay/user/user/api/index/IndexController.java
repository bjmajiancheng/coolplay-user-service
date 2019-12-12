package com.coolplay.user.user.api.index;

import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.user.model.BannerModel;
import com.coolplay.user.user.model.CircleModel;
import com.coolplay.user.user.model.CoolplayBaseModel;
import com.coolplay.user.user.service.IBannerService;
import com.coolplay.user.user.service.ICircleService;
import com.coolplay.user.user.service.ICoolplayBaseService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by majiancheng on 2019/10/20.
 */
@Controller
@RequestMapping("/api/index")
public class IndexController {

    @Autowired
    private ICircleService circleService;

    @Autowired
    private ICoolplayBaseService coolplayBaseService;

    @Autowired
    private IBannerService bannerService;

    @ResponseBody
    @RequestMapping(value = "/bannerList", method = RequestMethod.POST)
    public Result bannerList(BannerModel bannerModel) {

        try {
            int pageNo = 1;
            int pageSize = 5;
            PageInfo<BannerModel> pageInfo = this.bannerService.selectByFilterAndPage(bannerModel, pageNo, pageSize);

            return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/circleList", method = RequestMethod.POST)
    public Result circleList(CircleModel circleModel) {

        try {
            int pageNo = 1;
            int pageSize = 3;
            circleModel.setSort_("memberCnt_desc");
            PageInfo<CircleModel> pageInfo = this.circleService.selectByFilterAndPage(circleModel, pageNo, pageSize);

            return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/baseList", method = RequestMethod.POST)
    public Result baseList(CoolplayBaseModel coolplayBaseModel) {

        try {
            int pageNo = 1;
            int pageSize = 3;
            coolplayBaseModel.setSort_("readCnt_desc");
            PageInfo<CoolplayBaseModel> pageInfo = this.coolplayBaseService.selectByFilterAndPage(coolplayBaseModel, pageNo, pageSize);
            if(CollectionUtils.isNotEmpty(pageInfo.getList())) {
                for(CoolplayBaseModel baseModel : pageInfo.getList()) {
                    baseModel.setType(1);
                }
            }

            return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }
}
