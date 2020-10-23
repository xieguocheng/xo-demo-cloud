package com.xo.controller;

import com.xo.mapper.XoMapper;
import com.xo.model.Xo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: xo
 * @NAME: Hello
 * @DATE: 2020/8/7
 * @Description: Hello
 **/
@Slf4j
@Api(tags="测试接口模块")
@RestController
public class Hello {

    @Resource
    XoMapper xoMapper;

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        Xo xo = xoMapper.selectByPrimaryKey(1);
        System.out.println(xo);
        return xo.toString();
    }

}
