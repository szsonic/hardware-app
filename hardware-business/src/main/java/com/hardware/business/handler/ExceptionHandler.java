package com.hardware.business.handler;

import org.iframework.commons.utils.log.Log;
import org.iframework.support.domain.enums.ModStatusCode;
import org.iframework.support.domain.model.JsonResultMobModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Component
public class ExceptionHandler extends AbstractHandlerExceptionResolver {
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        response.setStatus(HttpStatus.OK.value()); //设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        response.setCharacterEncoding("UTF-8"); //避免乱码
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            Log.e("请求异常：",ex);
            response.getWriter().write(new JsonResultMobModel(ModStatusCode.FAILURE, ex.getMessage()).toJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mv;
    }
}
