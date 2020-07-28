package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.AwardExecution;
import com.imooc.o2o.dto.UserShopMapExecution;
import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserShopMap;
import com.imooc.o2o.service.AwardService;
import com.imooc.o2o.service.UserShopMapService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController("frontAwardController")
@RequestMapping(value = "/frontend")
public class AwardController {

    @Autowired
    private AwardService awardService;
    @Autowired
    private UserShopMapService userShopMapService;

    //獎品詳情
    @GetMapping(value ="/getawarddetail" )
    private Map<String, Object> getAwardDetail(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long awardId = HttpServletRequestUtil.getLong(request, "awardId");
        if (awardId > -1) {
            Award award = awardService.queryAwardById(awardId);
            modelMap.put("success", true);
            modelMap.put("award", award);
        } else {
            modelMap.put("success", false);
            modelMap.put("award", "empty awardId");
        }
        return modelMap;
    }

    @GetMapping(value = "/getawardlist")
    private Map<String, Object> getAwardList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        String awardName = HttpServletRequestUtil.getString(request, "awardName");
        Award awardCondition = new Award();
        awardCondition.setEnableStatus(1);
        awardCondition.setShopId(shopId);
        if (awardName != null) awardCondition.setAwardName(awardName);
        AwardExecution execution = awardService.queryAwardList(awardCondition, pageIndex, pageSize);
        modelMap.put("success", true);
        modelMap.put("count", execution.getCount());
        modelMap.put("awardList", execution.getAwardList());
        return modelMap;
    }

    @PostMapping(value = "/redeemAward")
    private Map<String, Object> redeemAward(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null && user.getUserId() != null) {
            long awardId = HttpServletRequestUtil.getLong(request, "awardId");
            long shopId = HttpServletRequestUtil.getLong(request, "shopId");
            if (awardId > -1L && shopId > -1L) {
                //封裝userShopMap對象
                Shop shop = new Shop();
                shop.setShopId(shopId);
                UserShopMap userShopMapCondition = new UserShopMap();
                userShopMapCondition.setUser(user);
                userShopMapCondition.setShop(shop);
                try{
                    UserShopMapExecution execution = userShopMapService.userRedeemAward(
                            userShopMapCondition, awardId);
                    if(execution.getState() ==1){
                        modelMap.put("success", true);
                    }else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", execution.getStateInfo());
                    }
                }catch (Exception e){
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.getMessage());
                }
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "請先登入");
            modelMap.put("redirect", true);
        }
        return modelMap;
    }
}
