package com.bishe.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bishe.common.Result;
import com.bishe.pojo.Notice;
import com.bishe.pojo.User;
import com.bishe.service.NoticeService;
import com.bishe.service.UserService;
import com.bishe.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    NoticeService noticeService;

    @Resource
    UserService userService;

    /**
     * 新增信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody Notice notice) {
        User currentUser = TokenUtils.getCurrentUser();  // 获取到当前登录的用户信息
        notice.setUserid(currentUser.getId());
        notice.setTime(DateUtil.now());
        noticeService.save(notice);
        return Result.success();
    }
    /**
     * 修改信息
     */
    @PutMapping("/update")
    public Result update(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return Result.success();
    }

    /**
     * 删除信息
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        noticeService.removeById(id);
        return Result.success();
    }


    /**
     * 批量删除信息
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        noticeService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询全部信息
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Notice> userList = noticeService.list(new QueryWrapper<Notice>().orderByDesc("id"));
        return Result.success(userList);
    }

    @GetMapping("/selectUserData")
    public Result selectUserData(){
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<Notice>().orderByDesc("id");
        queryWrapper.eq("open",1);
        List<Notice> notices = noticeService.list(queryWrapper);
        return Result.success(notices);
    }

    /**
     * 根据ID查询信息
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Notice notice = noticeService.getById(id);
        User user = userService.getById(notice.getUserid());
        if (user != null) {
            notice.setUser(user.getName());
        }
        return Result.success(notice);
    }


    /**
     * 多条件模糊查询信息
     * pageNum 当前的页码
     * pageSize 每页查询的个数
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String title) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<Notice>().orderByAsc("id");  // 默认倒序，让最新的数据在最上面
        queryWrapper.like(StrUtil.isNotBlank(title), "title", title);
        Page<Notice> page = noticeService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Notice> records = page.getRecords();
//        List<User> list = userService.list();
        for (Notice record : records) {
            Integer authorid = record.getUserid();
            User user = userService.getById(authorid);
//            String author = list.stream().filter(u -> u.getId().equals(authorid)).findFirst().map(User::getName).orElse("");
            if (user != null) {
                record.setUser(user.getName());
            }
        }
        return Result.success(page);
    }


}