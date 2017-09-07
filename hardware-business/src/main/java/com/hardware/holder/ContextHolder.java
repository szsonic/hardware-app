package com.hardware.holder;


import com.hardware.model.LoginInfo;

/**
 * <br>
 * 保存上下文的一些信息
 * @author sunzhongshuai
 */
public class ContextHolder {

    private static ThreadLocal<LoginInfo> loginInfoThreadLocal=new ThreadLocal<>();

    public static LoginInfo getLoginInfo(){
        return loginInfoThreadLocal.get();
    }

    public static void  setLoginInfo(LoginInfo loginInfo){
        loginInfoThreadLocal.set(loginInfo);
    }

    public static void  removeLoginInfo(){
        loginInfoThreadLocal.remove();
    }
}
