package com.hardware.service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/test")
public class TestController {
    Logger logger= LoggerFactory.getLogger(TestController.class);
    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public String test(String text){
        logger.info("我是日志");
        logger.error("我是错误日志");
        logger.debug("我是debug日志");
        logger.warn("我是警告日志");
        return text;
    }

}
