package com.support.controller;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 李世成
 * @email: lishicheng@innjia.com
 * @date: 2017/8/29
 */
public abstract class BaseControllerSupport {
    private final static Logger logger = LoggerFactory.getLogger(BaseControllerSupport.class);

    /**
     * 数据自动绑定及转换（如要自定义绑定则需要覆盖此方法）
     *
     * @param binder
     *            数据绑定对象
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        // binder.registerCustomEditor(String.class, new
        // CustomStringEscapeEditor(true, true, true));
    }

    /**
     * 获取请求地址及所带的参数字符串
     *
     * @param request
     *            HTTP请求对象
     * @return String 请求地址及所带的参数字符串
     */
    protected String getRequestURIAllQueryString(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (StringUtils.isNotEmpty(request.getQueryString())) {
            uri += "?" + request.getQueryString();
        }
        return uri;
    }

    /**
     * 获取HttpSession对象
     *
     * @param request
     *            HTTP请求对象
     * @return String 服务器所在物理路径字符串
     */
    protected HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }

    /**
     * 获取服务器的项目所在物理路径
     *
     * @param request
     *            HTTP请求对象
     * @return String 服务器所在物理路径字符串
     */
    protected String getRealPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("");
    }

    /**
     * 获取客户端IP地址
     *
     * @param request
     *            HTTP请求对象
     * @return String IP地址字符串
     */
    protected String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 渲染输出数据
     *
     * @param content
     *            输出内容字符串
     * @param contentType
     *            内容类型
     * @param response
     *            响应对象
     */
    private void render(String content, String contentType, HttpServletResponse response) {
        try {
            HttpServletResponse localHttpServletResponse = response;
            if (localHttpServletResponse != null) {
                localHttpServletResponse.setHeader("Pragma", "No-cache");
                localHttpServletResponse.setHeader("Cache-Control", "no-cache");
                localHttpServletResponse.setDateHeader("Expires", 0L);
                localHttpServletResponse.setContentType(contentType);
                localHttpServletResponse.getWriter().write(content);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 渲染输出Text文本
     *
     * @param content
     *            输出内容字符串
     * @param response
     *            响应对象
     */
    protected void renderText(String content, HttpServletResponse response) {
        render(content, "text/plain; charset=UTF-8", response);
    }

    /**
     * 渲染输出JSON文本
     *
     * @param content
     *            输出内容字符串
     * @param response
     *            响应对象
     */
    protected void renderJson(String content, HttpServletResponse response) {
        render(content, "application/json; charset=UTF-8", response);
    }

    /**
     * 渲染输出HTML文本
     *
     * @param content
     *            输出内容字符串
     * @param response
     *            响应对象
     */
    protected void renderHtml(String content, HttpServletResponse response) {
        render(content, "text/html; charset=UTF-8", response);
    }

    /**
     * 渲染输出Xml文本
     *
     * @param content
     *            输出内容字符串
     * @param response
     *            响应对象
     */
    protected void renderXml(String content, HttpServletResponse response) {
        render(content, "text/xml; charset=UTF-8", response);
    }

    /**
     * 设置header的Access-Control-Allow-Origin参数
     *
     * @param response
     *            响应对象
     */
    protected void setHeaderAccessControlAllowOrigin(HttpServletResponse response) {
//		response.setHeader("Access-Control-Allow-Origin", "*");
    }

    /**
     * 设置header的Access-Control-Allow-Origin参数(指定域名)
     *
     * @param response
     *            响应对象
     * @param domain
     *            域名字符串
     */
    protected void setHeaderAccessControlAllowOrigin(HttpServletResponse response,String domain) {
//		response.setHeader("Access-Control-Allow-Origin", ValidatorUtils.isNotEmpty(domain)?domain:"*");
    }

    /**
     * 批量设置HTTP header的参数
     *
     * @param params
     *            header的参数集合对象
     * @param response
     *            响应对象
     */
    protected void setHeaders(Map<String, String> params, HttpServletResponse response) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            response.setHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 显示输出请求参数，格式使用JSON格式
     * @param request
     */
    @SuppressWarnings("rawtypes")
    protected void showJsonReqParams(HttpServletRequest request){
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        logger.info("参数获取成功:");
        logger.info(new Gson().toJson(params));
    }
}
