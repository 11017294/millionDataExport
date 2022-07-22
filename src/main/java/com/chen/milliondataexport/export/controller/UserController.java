package com.chen.milliondataexport.export.controller;


import com.chen.milliondataexport.export.entity.User;
import com.chen.milliondataexport.export.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author MaybeBin
 * @since 2022-07-22
 */
@Api(value = "用户相关接口", tags = {"用户相关接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    public List<User> getUsers() {
        return userService.list();
    }

    @PostMapping("/dataExport")
    @ApiOperation(value = "导出用户表", notes = "导出用户表")
    public void dataExport(HttpServletResponse response) {
        try {
            userService.dataExport(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
