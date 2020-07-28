package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.UserProductMapExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserProductMap;
import com.imooc.o2o.service.UserProductMapService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/frontend")
public class UserProductController {
    @Autowired
    private UserProductMapService userProductMapService;

    @GetMapping(value = "/listuserproductmapsbycustomer")
    private Map<String, Object> listUserProductMapByCustomer(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //分頁訊息
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //用戶訊息
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (pageIndex > -1 && pageSize > -1 && user != null && user.getUserId() != null) {
            UserProductMap userProductMapConditon = new UserProductMap();
            userProductMapConditon.setUser(user);
            //如果有shopId，則列出該顧客在該店的消費紀錄
            long shopId = HttpServletRequestUtil.getLong(request, "shopId");
            if (shopId > -1) {
                Shop shop = new Shop();
                shop.setShopId(shopId);
                userProductMapConditon.setShop(shop);
            }
            //如果有商品名稱，則按照名稱模糊查詢消費紀錄
            String productName = HttpServletRequestUtil.getString(request, "productName");
            if (productName != null) {
                Product product = new Product();
                product.setProductName(productName);
                userProductMapConditon.setProduct(product);
            }
            UserProductMapExecution pe = userProductMapService.listUserProductMap(
                    userProductMapConditon, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("userProductMapList", pe.getUserProductMapList());
            modelMap.put("count", pe.getCount());

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty userId or pageIndex or pageSize");
        }
        return modelMap;
    }

    @PostMapping(value = "/buyproduct")
    private Map<String, Object> buyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        if (user != null && user.getUserId() != null && productId > -1L) {
            Product product = new Product();
            product.setProductId(productId);
            UserProductMap userProductMapCondition = new UserProductMap();
            userProductMapCondition.setUser(user);
            userProductMapCondition.setProduct(product);
            try {
                UserProductMapExecution execution = userProductMapService.insertUserPurchaseRecord(
                        userProductMapCondition);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }
        return modelMap;
    }
}
