package com.hardware.service.controller;


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

    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public String test(String text){
        return text;
    }

}
