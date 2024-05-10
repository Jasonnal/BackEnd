package com.bishe.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishe.mapper.RepairMapper;
import com.bishe.pojo.Examine;
import com.bishe.pojo.Repair;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RepairService extends ServiceImpl<RepairMapper, Repair> {

    @Resource
    private RepairMapper repairMapper;

    public Page<Examine> getMyApply(Integer uid, Page<Examine> page) {
        Page<Examine> myApply = repairMapper.getMyApply(uid, page);
        return myApply;
    }

    public Page<Examine> getAllApply(Page<Examine> page) {
        Page<Examine> allApply = repairMapper.getAllApply(page);
        return allApply;
    }
    public Boolean repairProcess(Integer cid){
        Boolean repairProcess = repairMapper.repairProcess(cid);
        return repairProcess;
    }
}
