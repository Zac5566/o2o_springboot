package com.imooc.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/frontend")
public class FrontendController {

    @GetMapping(value = "/index")
    private String index() {
        return "frontend/index";
    }

    @GetMapping(value = "/shoplist")
    private String shopList() {
        return "frontend/shoplist";
    }

    @GetMapping(value = "/productdetail")
    private String showProductDetail() {
        return "frontend/productdetail";
    }

    @GetMapping(value = "/shopdetail")
    private String shopDetail() {
        return "frontend/shopdetail";
    }

    @GetMapping(value = "/myrecord")
    private String myRecord() {
        return "frontend/myrecord";
    }

    @GetMapping(value = "/awarddetail")
    private String awardDetail() {
        return "frontend/awarddetail";
    }

    @GetMapping(value = "/awardlist")
    private String awardList() {
        return "frontend/awardlist";
    }

    @GetMapping(value = "/pointrecord")
    private String pointRecord() {
        return "frontend/pointrecord";
    }

    @GetMapping(value = "/mypoint")
    private String myPotint() {
        return "frontend/mypoint";
    }

    @GetMapping(value = "/sidepanel")
    private String sidePanel() {
        return "frontend/sidepanel";
    }
}
