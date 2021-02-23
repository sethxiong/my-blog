package com.seth.my.blog.service.impl;

import com.seth.my.blog.entity.User;
import com.seth.my.blog.mapper.UserMapper;
import com.seth.my.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sethxiong
 * @since 2021-02-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
