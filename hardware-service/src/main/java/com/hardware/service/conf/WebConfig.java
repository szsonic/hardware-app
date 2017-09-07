package com.hardware.service.conf;

import com.hardware.filter.xssFilter.XssAndSqlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.Filter;

/**
 * @author 李世成
 * @email: lishicheng@innjia.com
 * @date: 2017/8/28
 */
@Configuration
public class WebConfig {

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.addUrlMappings("*.do");
        registration.addUrlMappings("*.json");
        return registration;
    }

    /*********************************************sqlAndXssFilter***start************************************/
    @Bean(name = "xssAndSqlFilter")
    public Filter xssAndSqlFilter(){
        return new XssAndSqlFilter();
    }

    @Bean
    public FilterRegistrationBean registrationXssAndSqlFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(xssAndSqlFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("xssAndSqlFilter");
        return registration;
    }
    /*********************************************sqlAndXssFilter***end************************************/

    /*********************************************sessionFilter***start************************************/
    @Bean(name = "sessionFilter")
    public Filter sessionFilter(){
        return new XssAndSqlFilter();
    }

    @Bean
    public FilterRegistrationBean registrationSessionFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        return registration;
    }
    /*********************************************sessionFilter***end************************************/


}
