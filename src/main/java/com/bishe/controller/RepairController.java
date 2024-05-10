package com.bishe.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bishe.common.Result;
import com.bishe.pojo.Examine;
import com.bishe.pojo.Notice;
import com.bishe.pojo.Repair;
import com.bishe.pojo.User;
import com.bishe.service.RepairService;
import com.bishe.service.UserService;
import com.bishe.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repair")
public class RepairController {

    @Resource
    private RepairService repairService;


    /**
     * 新增信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody Repair repair) {
        repair.setRepairedTime(DateUtil.now());
        repairService.save(repair);
        return Result.success();
    }

    @GetMapping("/selectApply")
    public Result getMyApply(@RequestParam Integer uid,@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        Page<Examine> page = new Page<>(pageNum,pageSize);
        Page<Examine> myApply = repairService.getMyApply(uid, page);
        return Result.success(myApply);
    }

    @GetMapping("/selectAllApply")
    public Result getAllApply(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        Page<Examine> page = new Page<>(pageNum,pageSize);
        Page<Examine> allApply = repairService.getAllApply(page);
        return Result.success(allApply);
    }
    @PutMapping("/process")
    public Result process(@RequestBody Repair repair){
        boolean b = repairService.updateById(repair);
        return Result.success(b);
    }
    @PutMapping("/repairProcess/{cid}")
    public Result repairProcess(@PathVariable Integer cid){
        Boolean process = repairService.repairProcess(cid);
        return Result.success(process);
    }
}
