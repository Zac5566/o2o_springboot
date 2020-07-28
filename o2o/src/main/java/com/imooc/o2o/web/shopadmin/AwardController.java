package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.AwardExecution;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.AwardStateEnum;
import com.imooc.o2o.exceptions.AwardOperationException;
import com.imooc.o2o.service.AwardService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/shopadmin")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @Autowired
    private CommonsMultipartResolver multipartResolver;

    @PostMapping(value = "/addaward")
    private Map<String, Object> addAward(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "驗證碼錯誤");
            return modelMap;
        }
        //初始化變量
        ImageHolder thumbnail = null;
        ObjectMapper mapper = new ObjectMapper();
        Award award;
        //從servletContext中獲取文件流
        multipartResolver.setServletContext(request.getServletContext());
        //處裡縮圖
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = ImageUtil.imageHolder(request, thumbnail, null);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "縮圖不能為空");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //處理獎品
        try {
            String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
            award = mapper.readValue(awardStr, Award.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //訊息正確開始賦值
        try {
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            award.setShopId(currentShop.getShopId());
            AwardExecution awardExecution = awardService.addAward(award, thumbnail);
            if (awardExecution.getStateInfo() == AwardStateEnum.SUCCESS.getStateInfo()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", awardExecution.getStateInfo());
            }
        } catch (AwardOperationException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    @GetMapping(value = "/getawardlist")
    private Map<String, Object> getAwardList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //空值判斷
        if (currentShop != null && currentShop.getShopId() != null && pageIndex > -1
                && pageSize > -1) {
            //封裝查詢條件
            Award awardCondition = new Award();
            awardCondition.setShopId(currentShop.getShopId());
            AwardExecution awardExecution = awardService.queryAwardList(awardCondition
                    , pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("awardList", awardExecution.getAwardList());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shop or pageIndex or pageSize");
        }
        return modelMap;
    }

    @GetMapping(value = "/getaward")
    private Map<String, Object> getAward(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long awardId = HttpServletRequestUtil.getLong(request, "awardId");
        if (awardId > -1) {
            Award award = awardService.queryAwardById(awardId);
            modelMap.put("success", true);
            modelMap.put("award", award);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "獎品不存在");
        }
        return modelMap;
    }

    @PostMapping(value = "modifyaward")
    private Map<String, Object> modifyaward(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        boolean enableStatus = HttpServletRequestUtil.getBoolean(request, "statusChange");
        if (!enableStatus && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "error kaptcha");
            return modelMap;
        }
        //先去數據庫查看該獎品是否存在，屬於哪個店家
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //初始化變量
        ImageHolder thumbnail = null;
        ObjectMapper mapper = new ObjectMapper();
        Award award;
        //從servletContext中獲取文件流
        multipartResolver.setServletContext(request.getServletContext());
        //處裡縮圖
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = ImageUtil.imageHolder(request, thumbnail, null);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //處理獎品
        try {
            String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
            award = mapper.readValue(awardStr, Award.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //訊息正確開始賦值
        try {
            award.setShopId(currentShop.getShopId());
            AwardExecution awardExecution = awardService.modifyAward(award, thumbnail);
            if (awardExecution.getStateInfo() == AwardStateEnum.SUCCESS.getStateInfo()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", awardExecution.getStateInfo());
            }
        } catch (AwardOperationException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        return modelMap;
    }
}
