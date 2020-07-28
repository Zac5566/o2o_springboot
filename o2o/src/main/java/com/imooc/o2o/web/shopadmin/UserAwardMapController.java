package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.dto.UserAwardMapExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserAwardMap;
import com.imooc.o2o.entity.UserShopMap;
import com.imooc.o2o.enums.UserAwardMapStateEnum;
import com.imooc.o2o.service.UserAwardMapService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/shopadmin")
public class UserAwardMapController {

    @Autowired
    private UserAwardMapService userAwardMapService;

    /*
     * 店家得到兌換獎品資訊:
     *   顧客名單
     *   使用紀錄
     * */
    @GetMapping(value = "/getawarddelivercheck")
    private Map<String, Object> getAwardDeliverCheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        String name = HttpServletRequestUtil.getString(request, "name");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //必要值的空值判斷
        if (currentShop != null && currentShop.getShopId() != null && pageIndex > -1 && pageSize > -1) {
            UserAwardMap userAwardMapCondition = new UserAwardMap();
            userAwardMapCondition.setShop(currentShop);
            //如果需要模糊查詢顧客
            if (name != null) {
                PersonInfo user = new PersonInfo();
                user.setName(name);
                userAwardMapCondition.setUser(user);
            }
            UserAwardMapExecution execution = userAwardMapService.listUserAwardMap(userAwardMapCondition
                    , pageIndex, pageSize);
            //封裝結果
            modelMap.put("success", true);
            modelMap.put("userAwardMapList", execution.getUserAwardMapList());
            modelMap.put("count", execution.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "emptyShop or pageIndex or pageSize");
        }
        return modelMap;
    }
}
