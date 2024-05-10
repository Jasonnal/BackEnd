package com.bishe.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bishe.common.AuthAccess;
import com.bishe.common.Result;
import com.bishe.pojo.BookingCountType;
import com.bishe.pojo.Classroom;
import com.bishe.pojo.User;
import com.bishe.service.ClassroomService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class ClassroomController {

    @Resource
    private ClassroomService classroomService;


    @PostMapping("/add")
    public Result add(@RequestBody Classroom classroom) {
        classroomService.add(classroom);
        return Result.success("教室添加成功");
    }
    @PutMapping("/update")
    public Result update(@RequestBody Classroom classroom) {
        //System.out.println(classroom);
        classroomService.updateById(classroom);
        return Result.success("教室更新成功");
    }
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        classroomService.removeById(id);
        return Result.success("按照ID删除成功");
    }
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        classroomService.deleteBatch(ids);
        return Result.success("批量删除成功");
    }

    @GetMapping("/selectFree")
    public Result selectFree(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String name,
                             @RequestParam String type) {
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<Classroom>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        queryWrapper.like(StrUtil.isNotBlank(type), "type", type);
        queryWrapper.eq("open", 1);
        Page<Classroom> page = classroomService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);

    }

    @GetMapping("/getId")
    public Result getIdByName(@RequestParam String name) {
        Integer id = classroomService.getIdBYName(name);
        return Result.success(id);
    }


    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String name,
                               @RequestParam String type) {
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<Classroom>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        queryWrapper.like(StrUtil.isNotBlank(type), "type", type);
        Page<Classroom> page = classroomService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);


    }

    //获取所有教室名
    @AuthAccess
    @GetMapping("/getAllRoom")
    public Result selectAllRoom() {
        List<String> rooms = classroomService.selectAllRoom();
        return Result.success(rooms);
    }
    @AuthAccess
    @GetMapping("/getBookingTypeCount")
    public Result getBookingTypeCount(){
        List<BookingCountType> bookingCountType = classroomService.getBookingCountType();
        return Result.success(bookingCountType);
    }

}
