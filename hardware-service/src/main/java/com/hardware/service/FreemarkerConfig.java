package com.hardware.service;

import com.utils.XmlEscape;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.*;

/**
 * @author 李世成
 * @email: lishicheng@innjia.com
 * @date: 2017/8/24
 */
//@org.springframework.context.annotation.Configuration
public class FreemarkerConfig {
    //基础路径设置
    protected final static String BASE_PATH = "/hardware-service";
    protected final static String BASE_RES_PATH = "/hardware-res";
    protected final static String TEMPLATE_LOAD_PATH = "classpath:/templates/views/";

    //freemarker基本设置
    protected final static String TAG_SYNTAX = "auto_detect";
    protected final static String TEMPLATE_UPDATE_DELAY ="1";
    protected final static String DEFAULT_ENCODING = "UTF-8";
    protected final static String OUTPUT_ENCODING = "UTF-8";
    protected final static String LOCALE = "zh_CN";
    protected final static String NUMBER_FORMAT = "##0";
    protected final static String DATE_FORMAT = "yyyy-MM-dd";
    protected final static String TIME_FORMAT = "HH:mm:ss";
    protected final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    protected final static String CLASSIC_COMPATIBLE = "true";


    @Bean("freeMarkerConfigurer")
    public FreeMarkerConfigurer freeMarkerConfigurer(){
        FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();
        Map<String,Object> freemarkerVaribales = new HashMap<>();
        com.utils.XmlEscape xmlEscape = new XmlEscape();
        freemarkerVaribales.put("xml_escape",xmlEscape);
        freemarkerVaribales.put("basePath",BASE_PATH);
        freemarkerVaribales.put("resPath",BASE_RES_PATH);
        //暂不加载bussinessUtil
        //freemarkerVaribales.put("common",businessUtil);
        freeMarkerConfigurer.setFreemarkerVariables(freemarkerVaribales);
        freeMarkerConfigurer.setTemplateLoaderPath(TEMPLATE_LOAD_PATH);
        freeMarkerConfigurer.setFreemarkerSettings(getBaseFreemarkerConfig());
        return freeMarkerConfigurer;
    }
//    @Bean("freeMarkerViewResolverList")
//    public List <ViewResolver> freeMarkerViewResolverList(){
//        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
//        List <ViewResolver> list = new ArrayList<>();
//        freeMarkerViewResolver.setCache(false);
//        freeMarkerViewResolver.setPrefix("ffff");
//        freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
//        freeMarkerViewResolver.setRequestContextAttribute("request");
//        freeMarkerViewResolver.setSuffix(".json.ftl");
//        freeMarkerViewResolver.setContentType("application/json;charset=UTF-8;");
//        list.add(freeMarkerViewResolver);
//        return list;
//    }
    @Bean("contentNegotiatingViewResolver")
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(){
        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        List <ViewResolver> list = new ArrayList<>();
        freeMarkerViewResolver.setCache(false);
        freeMarkerViewResolver.setPrefix("ffff");
        freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
        freeMarkerViewResolver.setRequestContextAttribute("request");
        freeMarkerViewResolver.setSuffix(".json.ftl");
        freeMarkerViewResolver.setContentType("application/json;charset=UTF-8;");
        list.add(freeMarkerViewResolver);
        viewResolver.setViewResolvers(list);
        return viewResolver;
    }

    private static Properties getBaseFreemarkerConfig(){
        Properties properties = new Properties();
        properties.setProperty("tag_syntax",TAG_SYNTAX);
        properties.setProperty("template_update_delay",TEMPLATE_UPDATE_DELAY);
        properties.setProperty("default_encoding",DEFAULT_ENCODING);
        properties.setProperty("output_encoding",OUTPUT_ENCODING);
        properties.setProperty("locale",LOCALE);
        properties.setProperty("number_format",NUMBER_FORMAT);
        properties.setProperty("date_format",DATE_FORMAT);
        properties.setProperty("time_format",TIME_FORMAT);
        properties.setProperty("datetime_format",DATETIME_FORMAT);
        properties.setProperty("classic_compatible",CLASSIC_COMPATIBLE);
        return properties;

    }



}
