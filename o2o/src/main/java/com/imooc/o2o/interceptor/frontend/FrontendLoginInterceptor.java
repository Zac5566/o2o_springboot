package com.imooc.o2o.interceptor.frontend;


import com.imooc.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class FrontendLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null && user.getUserId() != null) {
            return true;
        }
        //若不滿足條件則跳轉到登入頁面
//        PrintWriter out = response.getWriter();
//        out.println("<html>");
//        out.println("<script>");
//        out.println("window.location.href ='" + request.getContextPath() + "/local/login?usertype=1';");
//        out.println("</script>");
//        out.println("</html>");
        response.sendRedirect(request.getContextPath()+"/local/login?usertype=1");
        return false;


    }
}
