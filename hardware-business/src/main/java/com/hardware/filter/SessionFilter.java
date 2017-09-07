package com.hardware.filter;


import com.hardware.holder.ContextHolder;
import com.hardware.model.LoginInfo;
import com.hardware.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class SessionFilter implements Filter {
    @Override
    public void destroy() {
        ContextHolder.removeLoginInfo();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        //排除登录请求
        if(request.getRequestURI().contains("checkLogin")){
            chain.doFilter(req,res);
            return;
        }
        if(ContextHolder.getLoginInfo()== null) {
            LoginInfo loginInfo=new LoginInfo();
            loginInfo.setIp(request.getRemoteAddr());
            //获取ip信息
            HttpSession session = request.getSession();
            if(session.getAttribute( "adminSessionInfo")!= null) {
                User user= (User)session.getAttribute( "adminSessionInfo");
                loginInfo.setUser(user);
                //获取当前登录用户
            }
            ContextHolder.setLoginInfo(loginInfo);
        }
        chain.doFilter(req,res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
