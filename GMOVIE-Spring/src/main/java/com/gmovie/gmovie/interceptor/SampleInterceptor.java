package com.gmovie.gmovie.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class SampleInterceptor implements HandlerInterceptor {

//     /***
//      * 在請求處理之前進行調用(Controller方法調用之前)
//      */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        System.out.println("執行了攔截器的preHandle方法");
        try {
            HttpSession session = request.getSession();
            //統一攔截（查詢當前session是否存在user）(這裏user會在每次登錄成功後，寫入session)
            Integer userNo = (Integer) session.getAttribute("userNo");
            System.out.println(userNo);
            if (userNo != null) {
                return true;
            }
            response.sendRedirect(request.getContextPath() + "login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        //如果設置爲false時，被請求時，攔截器執行到此處將不會繼續操作
        //如果設置爲true時，請求將會繼續執行後面的操作
    }

    /***
     * 請求處理之後進行調用，但是在視圖被渲染之前（Controller方法調用之後）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("執行了攔截器的postHandle方法");
    }

    /***
     * 整個請求結束之後被調用，也就是在DispatchServlet渲染了對應的視圖之後執行（主要用於進行資源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("執行了攔截器的afterCompletion方法");
    }

}

