package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.dto.UserProductMapExecution;
import com.imooc.o2o.dto.UserShopMapExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserShopMap;
import com.imooc.o2o.enums.UserProductMapStateEnum;
import com.imooc.o2o.enums.UserShopMapStateEnum;
import com.imooc.o2o.service.UserShopMapService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//處理點數相關的控制器
@RestController
@RequestMapping(value = "/shopadmin")
public class UserAwardPointController {

    @Autowired
    private UserShopMapService userShopMapService;

    /**
     * 店家取得在該店消費的用戶的點數
     *
     * @param request:
     * @Author: Zac5566
     * @Date: 2020/7/14
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    @GetMapping(value = "/getuserpointlist")
    private Map<String, Object> getUserPointList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (currentShop != null && currentShop.getShopId() != null && pageIndex > -1 && pageSize > -1) {
            UserShopMap userShopMapCondition = new UserShopMap();
            userShopMapCondition.setShop(currentShop);
            UserShopMapExecution execution = userShopMapService.listUserShopMap(
                    userShopMapCondition, pageIndex, pageSize);
            if (execution.getState() == UserShopMapStateEnum.SUCCESS.getState()){
                modelMap.put("success", true);
                modelMap.put("count", execution.getCount());
                modelMap.put("userProductMapList", execution.getUserShopMapList());
            } else{
                modelMap.put("success", false);
                modelMap.put("errMsg", execution.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId or pageIndex or pageSize");
        }
        return modelMap;
    }
}
