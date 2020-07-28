package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("ShopAdminController")
@RequestMapping(value = "/shopadmin")
public class ShopAdminController {
    
    @GetMapping(value = "/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";
    }

    @GetMapping(value = "/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }

    @GetMapping(value = "/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }

    @GetMapping(value = "/productcategorymanagement")
    public String productManagement() {
        return "shop/productcategorymanagement";
    }

    @GetMapping(value = "/productoperation")
    public String productOperation() {
        return "shop/productoperation";
    }

    @GetMapping(value = "/productmanagement")
    public String productManageMent() {
        return "shop/productmanagement";
    }

    @GetMapping(value = "/productbuycheck")
    public String productBuyCheck() {
        return "shop/productbuycheck";
    }

    @GetMapping(value = "/awardmanagement")
    public String awardManagement() {
        return "shop/awardmanagement";
    }

    @GetMapping(value = "/awardoperation")
    public String awardOperation() {
        return "shop/awardoperation";
    }

    @GetMapping(value = "/usershopcheck")
    public String userPointList() {
        return "shop/usershopcheck";
    }

    @GetMapping(value = "/awarddelivercheck")
    public String awardDeliverCheck() {
        return "shop/awarddelivercheck";
    }
}
