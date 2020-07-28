package com.imooc.o2o.config.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.imooc.o2o.interceptor.frontend.FrontendLoginInterceptor;
import com.imooc.o2o.interceptor.shopadmin.ShopLoginInterceptor;
import com.imooc.o2o.interceptor.shopadmin.ShopPermissionInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc//等同於<mvc:annotation-driven/>
public class MvcConfiguration implements ApplicationContextAware,WebMvcConfigurer {

    //Spring容器
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //靜態資源配置
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/C:/Users/l5125/Desktop/img/upload/");
    }
    //靜態資源配置
    //<mvc:default-servlet-handler/>
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean(name = "viewResolver")
    public ViewResolver createViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        //設置Spring容器，從applicationContext中獲取bean
        viewResolver.setApplicationContext(applicationContext);
        viewResolver.setCache(false);
        viewResolver.setPrefix("/WEB-INF/html/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }
    //文件上傳解析器
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(20971520);
        return multipartResolver;
    }

    //kaptcha
    @Value("${kaptcha.border}")
    private String border;
    @Value("${kaptcha.textproducer.font.color}")
    private String fColor;
    @Value("${kaptcha.textproducer.font.size}")
    private String fSize;
    @Value("${kaptcha.image.width}")
    private String iWidth;
    @Value("${kaptcha.image.height}")
    private String iHeight;
    @Value("${kaptcha.image.names}")
    private String iNames;
    @Value("${kaptcha.textproducer.char.length}")
    private String charLength;

    //kaptcha servlet配置
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(),"/kaptcha");
        servlet.addInitParameter("kaptcha.border",border);
        servlet.addInitParameter("kaptcha.textproducer.font.color",fColor);
        servlet.addInitParameter("kaptcha.textproducer.font.size",fSize);
        servlet.addInitParameter("kaptcha.image.width",iWidth);
        servlet.addInitParameter("kaptcha.image.height",iHeight);
        servlet.addInitParameter("kaptcha.image.names",iNames);
        servlet.addInitParameter("kaptcha.textproducer.char.length",charLength);
        return servlet;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //後台登入攔截
        InterceptorRegistration loginInterceptor = registry.addInterceptor(new ShopLoginInterceptor());
        loginInterceptor.addPathPatterns("/shopadmin/**");
        //店鋪操作權限攔截
        InterceptorRegistration shopOperationInterceptor = registry.addInterceptor(new ShopPermissionInterceptor());
        shopOperationInterceptor.addPathPatterns("/shopadmin/**");
        //排除頁面
        //shoplist
        shopOperationInterceptor.excludePathPatterns("/shopadmin/shoplist");
        shopOperationInterceptor.excludePathPatterns("/shopadmin/getshoplist");
        //增加店鋪
        shopOperationInterceptor.excludePathPatterns("/shopadmin/shopoperation");
        shopOperationInterceptor.excludePathPatterns("/shopadmin/registershop");
        shopOperationInterceptor.excludePathPatterns("/shopadmin/getshopinitinfo");
        //管理店鋪
        shopOperationInterceptor.excludePathPatterns("/shopadmin/shopmanagement");
        shopOperationInterceptor.excludePathPatterns("/shopadmin/getshopmanagementinfo");
//        //前台攔截
        InterceptorRegistration frontendLoginInterceptor = registry.addInterceptor(new FrontendLoginInterceptor());
//        //用戶資料相關
        frontendLoginInterceptor.addPathPatterns("/local/changepsw");
        frontendLoginInterceptor.addPathPatterns("/frontend/myrecord");
        frontendLoginInterceptor.addPathPatterns("/frontend/mypoint");
        frontendLoginInterceptor.addPathPatterns("/frontend/pointrecord");
        frontendLoginInterceptor.addPathPatterns("/o2o/frontend/pointrecord");
//        //商品相關
        frontendLoginInterceptor.addPathPatterns("/frontend/buyproduct");
        frontendLoginInterceptor.addPathPatterns("/frontend/redeemAward");
    }
}
