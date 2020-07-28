package com.imooc.o2o.web.frontend;

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.service.UserShopMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/frontend")
public class UserShopMapController {

    @Autowired
    private UserShopMapService userShopMapService;

    @GetMapping(value = "/getmyrecord")
    private Map<String, Object> getMyRecord(HttpServletRequest request) {
        Map<String, Object> modleMap = new HashMap<String, Object>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null && user.getUserId() != null){

        }else {
            modleMap.put("success",false);
            modleMap.put("errMsg","empty user");
        }
            return modleMap;
    }

}


