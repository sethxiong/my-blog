package com.seth.my.blog.service.impl;

import com.seth.my.blog.entity.Blog;
import com.seth.my.blog.mapper.BlogMapper;
import com.seth.my.blog.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
