package com.seth.my.blog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seth.my.blog.entity.User;
import com.seth.my.blog.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * <p>
 * Description: 数据连接测试
 * </p>
 * @author sethxiong
 * @time 2021-02-24 01:25:03
 * @see com.seth.my.blog
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBlogApplication.class)
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void select() {
        System.out.println(userService.getOne(new QueryWrapper<User>().eq("id", "1")));
    }
}
