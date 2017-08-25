package com.hardware.service.controller;

import com.hardware.dao.ManyRepository;
import com.hardware.dao.OneRepository;
import com.hardware.model.Many;
import com.hardware.model.One;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    OneRepository oneRepository;
    @Autowired
    ManyRepository manyRepository;
    @Autowired
    ContentNegotiatingViewResolver contentNegotiatingViewResolver;

    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView Test(HttpServletRequest request, HttpServletResponse response){
        List <One> oneList = oneRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("/test");
        modelAndView.addObject("oneList",oneList);

        return modelAndView;
    }
}
