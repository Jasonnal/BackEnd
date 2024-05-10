package com.bishe.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishe.mapper.ClassroomMapper;
import com.bishe.pojo.BookingCountType;
import com.bishe.pojo.Classroom;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService extends ServiceImpl<ClassroomMapper,Classroom> {
    @Resource
    ClassroomMapper classroomMapper;


    //根具教室名查询
    public Classroom selectByName(String name){
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return getOne(queryWrapper);
    }

    public boolean add(Classroom classroom){
        return save(classroom);
    }

    public boolean update(Classroom classroom){
        return updateById(classroom);
    }

    public boolean deleteBatch(List<Integer> ids){
        return removeBatchByIds(ids);
    }


    public Integer getIdBYName(String name) {
        Integer id = classroomMapper.getIdaByName(name);
        return id;
    }
    public List<String> selectAllRoom(){
        return classroomMapper.selectAllRoom();
    }

    public List<BookingCountType> getBookingCountType(){
        return classroomMapper.getBookingCountType();
    }


}
