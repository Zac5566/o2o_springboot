package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.UserAwardMapExecution;
import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.UserAwardMap;
import com.imooc.o2o.service.UserAwardMapService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/frontend")
public class MyAwardMapController {

    @Autowired
    private UserAwardMapService userAwardMapService;

    @GetMapping(value = "/getmyawardlist")
    private Map<String, Object> getMyAwardList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        String awardName = HttpServletRequestUtil.getString(request, "awardName");
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null && user.getUserId() != null && pageIndex > -1 && pageSize > -1) {
            UserAwardMap userAwardMapCondition = new UserAwardMap();
            userAwardMapCondition.setUser(user);
            if (awardName != null){
                Award award = new Award();
                award.setAwardName(awardName);
                userAwardMapCondition.setAward(award);
            }
                UserAwardMapExecution execution = userAwardMapService.listUserAwardMap(userAwardMapCondition
                        , pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("userAwardList", execution.getUserAwardMapList());
            modelMap.put("count", execution.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageIndex or pageSize or user");
        }
        return modelMap;
    }
}
