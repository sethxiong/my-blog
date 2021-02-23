package com.seth.my.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seth.my.blog.common.Result;
import com.seth.my.blog.entity.User;
import com.seth.my.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Sethxiong
 * @since 2021-02-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Result test(@PathVariable Long id) {
        return Result.succ(userService.getOne(new QueryWrapper<User>().eq("id", id)));
    }

}
