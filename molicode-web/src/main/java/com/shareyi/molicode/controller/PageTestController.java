package com.shareyi.molicode.controller;

import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.exception.DefaultExceptionMaker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 页面功能
 *
 * @author david
 * @date 2018/3/28
 */
@Controller
@RequestMapping("/")
public class PageTestController {


    @RequestMapping(value = "/errorTest")
    @ResponseBody
    public String error(ModelMap context) {
        throw DefaultExceptionMaker.buildException("故意抛出异常", ResultCodeEnum.ERROR);
    }

    @RequestMapping(value = "/vmHello")
    public String vmHello(ModelMap context) {
        context.put("hello", "Hello world");
        context.put("now", new Date());
        return "hello";
    }
}
