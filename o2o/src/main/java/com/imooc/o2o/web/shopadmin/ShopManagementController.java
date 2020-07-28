package com.imooc.o2o.web.shopadmin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("shopManagementController")
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    CommonsMultipartResolver multipartResolver;

    @GetMapping(value = "/getshopmanagementinfo")
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        //判斷有無傳進shopId
        if (shopId < 0) {
            //沒有則在session中找
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                //session中找不到代表非法訪問
                modelMap.put("redirect", true);
                modelMap.put("url", "/o2o/shop/shopList");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                request.getSession().setAttribute("currentShop", currentShop);
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            modelMap.put("redirect", false);
            modelMap.put("currentShop", currentShop);
            request.getSession().setAttribute("currentShop", currentShop);
        }
        return modelMap;
    }

    @GetMapping(value = "/getshoplist")
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //獲取用戶訊息
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        try {
            //封裝查詢條件
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            request.getSession().setAttribute("shopList", se.getShopList());
            modelMap.put("shopList", se.getShopList());
            modelMap.put("success", true);
            modelMap.put("user", user);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @GetMapping(value = "/getshopbyid")
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.queryByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    @GetMapping(value = "/getshopinitinfo")
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    //註冊店鋪
    @PostMapping(value = "/registershop")
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "驗證碼輸入錯誤");
            return modelMap;
        }
        //1.接收並轉換相應的參數，包括店鋪訊息和圖片
        //前台來的json
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        //jackson-databind的類
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            // 普通項(json)轉shop
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //圖片項，springmvc接收的檔案為CommonsMultipartFile
        CommonsMultipartFile shopImg = null;
        //request.getSession().getServletContext()其實跟request.getServletContext()是一樣的，都是獲取上下文路徑
        multipartResolver.setServletContext(request.getServletContext());
        //是否有上傳文件流
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上傳圖片不能為空");
            return modelMap;
        }
        //2.註冊店鋪
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                ImageHolder thumbnail = new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
                se = shopService.addShop(shop, thumbnail);
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    //該用戶可以操作的店舖列表
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<Shop>();
                    }
                    shopList.add(shop);
                    request.getSession().setAttribute("shopList", shopList);

                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "請輸入店鋪訊息");
        }
        return modelMap;
    }

    //修改店鋪
    @PostMapping(value = "/modifyshop")
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "驗證碼輸入錯誤");
            return modelMap;
        }
        //1.接收並轉換相應的參數，包括店鋪訊息和圖片
        //前台來的json
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        //jackson-databind的類
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            // 普通項(json)轉shop
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //圖片項，springmvc接收的檔案為CommonsMultipartFile
        CommonsMultipartFile shopImg = null;
        multipartResolver.setServletContext(request.getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        //2.修改店鋪
        if (shop != null && shop.getShopId() != null) {
            ShopExecution se;
            try {
                if (shopImg != null) {
                    ImageHolder thumbnail = new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
                    se = shopService.updateShop(shop, thumbnail);
                } else {
                    se = shopService.updateShop(shop, null);
                }
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
                return modelMap;
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "請輸入店鋪id");
        }
        return modelMap;
    }
}
