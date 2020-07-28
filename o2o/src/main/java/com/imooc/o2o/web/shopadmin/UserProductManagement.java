package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.dto.EchartSeries;
import com.imooc.o2o.dto.EchartXAxis;
import com.imooc.o2o.dto.UserProductMapExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductSellDaily;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserProductMap;
import com.imooc.o2o.service.ProductSellDailyService;
import com.imooc.o2o.service.UserProductMapService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController("userProductManagement")
@RequestMapping("/shopadmin")
public class UserProductManagement {

    @Autowired
    private UserProductMapService userProductMapService;
    @Autowired
    private ProductSellDailyService productSellDailyService;

    @GetMapping(value = "/listuserproductmapbyshop")
    private Map<String, Object> listUserProductMapByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //分頁訊息
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //空值判斷
        if (currentShop != null && currentShop.getShopId() != null && pageIndex > -1
                && pageSize > -1) {
            String productName = HttpServletRequestUtil.getString(request, "productName");
            UserProductMap userProductMapCondition = new UserProductMap();
            userProductMapCondition.setShop(currentShop);
            //判斷有無輸入商品名
            if (productName != null) {
                Product product = new Product();
                product.setProductName(productName);
                userProductMapCondition.setProduct(product);
            }
            UserProductMapExecution ue = userProductMapService.listUserProductMap(
                    userProductMapCondition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("userProductMapList", ue.getUserProductMapList());
            modelMap.put("count", ue.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId or pageIndex or pageSize");
        }
        return modelMap;
    }

    @GetMapping(value = "listproductselldailybyshop")
    private Map<String, Object> listProductSellDailyByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() != null) {
            ProductSellDaily productSellDailyCondition = new ProductSellDaily();
            productSellDailyCondition.setShop(currentShop);
            //endTime
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            Date endTime = calendar.getTime();
            //beginTime(-1-6=-7)
            calendar.add(Calendar.DATE, -6);
            Date beginTime = calendar.getTime();
            //7天的商品結果
            List<ProductSellDaily> productSellDailyList = productSellDailyService
                    .listProductSellDaily(productSellDailyCondition, beginTime, endTime);
            //拼接echart所需要的數據
            //日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //legend數據
            HashSet<String> legendData = new LinkedHashSet<String>();
            //x軸數據
            HashSet<String> xData = new LinkedHashSet<String>();
            //series
            List<EchartSeries> series = new ArrayList<EchartSeries>();
            //日銷量
            List<Integer> totalList = new ArrayList<Integer>();
            //當前商品名稱
            String currentProductName = "";
            /*
             * data:
             *   productId   date    total
             *           1   01/02       2
             *           1   01/03       3
             *           1   01/04       1
             *           2   01/02       2
             *  .....
             * */
            for (int i = 0; i < productSellDailyList.size(); i++) {
                ProductSellDaily productSellDaily = productSellDailyList.get(i);
                legendData.add(productSellDaily.getProduct().getProductName());
                xData.add(sdf.format(productSellDaily.getCreateTime()));
                if (!currentProductName.equals(productSellDaily.getProduct().getProductName())
                        && !currentProductName.isEmpty()) {
                    //產品id轉換的時候(到productId = 2 時)
                    EchartSeries es = new EchartSeries();
                    //currentProductName還是產品轉換前的數據
                    es.setName(currentProductName);
                    //產品轉換前的數據，全部複製出來並寫入series中
                    es.setData(totalList.subList(0,totalList.size()));
                    series.add(es);
                    //置空totalList以便後續接收新數據
                    totalList = new ArrayList<Integer>();
                    //轉換productName
                    currentProductName = productSellDaily.getProduct().getProductName();
                    totalList.add(productSellDaily.getTotal());

                } else {
                    //產品id固定的時候
                    currentProductName = productSellDaily.getProduct().getProductName();
                    totalList.add(productSellDaily.getTotal());
                }
                if(i == (productSellDailyList.size()-1)){
                    //最後一筆id無法轉換，手動加入
                    EchartSeries es = new EchartSeries();
                    es.setName(currentProductName);
                    es.setData(totalList.subList(0,totalList.size()));
                    series.add(es);
                }
            }
            modelMap.put("success",true);
            modelMap.put("legendData",legendData);
            //前台xAxis需要的是array,所以這邊用list來拼裝
            List<EchartXAxis> xAxis = new ArrayList<EchartXAxis>();
            EchartXAxis ex = new EchartXAxis();
            ex.setData(xData);
            xAxis.add(ex);
            modelMap.put("xAxis",xAxis);
            modelMap.put("series",series);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shop");
        }
        return modelMap;
    }
}
