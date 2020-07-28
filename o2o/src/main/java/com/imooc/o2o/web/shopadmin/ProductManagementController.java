package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@RestController("ProductManagementController")
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    //定義最大詳情圖片
    private static final int IMAGEMAXCOUNT = 6;

    @Autowired
    private CommonsMultipartResolver multipartResolver;

    @PostMapping(value = "/addproduct")
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //校驗驗證碼
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "驗證碼錯誤");
            return modelMap;
        }
        //初始化變量
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> thumbnailList = new ArrayList<ImageHolder>();
        multipartResolver.setServletContext(request.getServletContext());
        //處理圖片
        try {
            //詳情圖list
            if (multipartResolver.isMultipart(request)) {
                thumbnail = ImageUtil.imageHolder(request, thumbnail, thumbnailList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上傳圖片不能為空");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        //處理product類
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        //若訊息接正確，則開始賦值
        try {
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            product.setShop(currentShop);
            ProductExecution pe = productService.addProduct(product, thumbnail, thumbnailList);
            if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
            }
        } catch (ProductOperationException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        return modelMap;
    }

    @GetMapping(value = "/getproductbyid")
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList = productCategoryService
                    .getProductCategoryByShopId(product.getShop().getShopId());
            modelMap.put("success", true);
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }

    @PostMapping(value = "/modifyproduct")
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //兩種編輯方式1.編輯全部2.只編輯狀態(上下架)
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        //指編輯狀態不用輸入驗證碼
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "驗證碼錯誤");
        }
        //初始化變量(
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        multipartResolver.setServletContext(request.getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = ImageUtil.imageHolder((MultipartHttpServletRequest) request, thumbnail, productImgList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            //實體類賦值
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                    return modelMap;
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品不能為空");
        }
        return modelMap;
    }

    @GetMapping(value = "/getproductlistbyshop")
    private Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //接收參數:商品名(模糊)、商品狀態、店鋪ID、商品類別
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    /**
     * 封裝商品條件
     * @param shopId:
     * @param productCategoryId:
     * @param productName:
     * @Author: Zac5566
     * @Date: 2020/6/24
     * @return: com.imooc.o2o.entity.Product
     */
    private Product compactProductCondition(Long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if ((productCategoryId != -1L)) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }
}
