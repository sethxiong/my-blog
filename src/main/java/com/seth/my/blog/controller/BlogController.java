package com.seth.my.blog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seth.my.blog.common.Result;
import com.seth.my.blog.entity.Blog;
import com.seth.my.blog.service.BlogService;
import com.seth.my.blog.util.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Sethxiong
 * @since 2021-02-24
 */
@Api(tags = "博客信息管理")
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 分页
     *
     * @param currentPage
     * @return
     */
    @ApiOperation("分页查询")
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        // 每页 5 条记录
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageData);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该文章已被删除");
        return Result.succ(blog);
    }

    /**
     * 编辑和添加，根据是否存在 id 区分功能
     *  @Validated 校验注解在 @RequiresAuthentication 认证注解之前执行
     * @param blog
     * @return
     */
    @RequiresAuthentication
    @PostMapping("/blog/edit/")
    public Result edit(@Validated @RequestBody Blog blog) {

        Blog tmp;
        // 添加
        if (null == blog.getId()) {
            tmp = new Blog();
            // 默认值
            tmp.setUserId(ShiroUtil.getProfile().getId())
                    .setCreated(LocalDateTime.now())
                    .setStatus(0);
        }
        // 编辑
        else {
            tmp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            Assert.isTrue(tmp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue()
                    , "没有权限编辑该文章");
        }

        // 保存
        BeanUtil.copyProperties(blog, tmp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(tmp);

        return Result.succ(null);
    }

    /**
     * 删除，只能删除自己发布的文章
     *
     * @param id
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/blog/delete/{id}")
    public Result delete(@PathVariable Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "文章不存在");
        Assert.isTrue(blog.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue()
                , "您没有权限删除该文章");
        boolean b = blogService.removeById(id);
        if (b) {
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }
    }
}
