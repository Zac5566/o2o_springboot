package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.UserShopMapExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserShopMap;
import com.imooc.o2o.service.UserShopMapService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController("myPointController")
@RequestMapping(value = "/frontend")
public class MyPointController {

    @Autowired
    private UserShopMapService userShopMapService;

    @GetMapping(value = "/getmypoint")
    private Map<String, Object> getMyPoint(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        String shopName = HttpServletRequestUtil.getString(request, "shopName");
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null && user.getUserId() != null && pageIndex > -1 && pageSize > -1) {
            UserShopMap userShopMapCondition = new UserShopMap();
            userShopMapCondition.setUser(user);
            if (shopName != null) {
                Shop shop = new Shop();
                shop.setShopName(shopName);
                userShopMapCondition.setShop(shop);
            }
            UserShopMapExecution execution = userShopMapService.listUserShopMap(userShopMapCondition
                    , pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("userPointList", execution.getUserShopMapList());
            modelMap.put("count", execution.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty user");
        }
        return modelMap;
    }
}
