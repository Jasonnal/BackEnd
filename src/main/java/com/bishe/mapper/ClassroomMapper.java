package com.bishe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bishe.pojo.BookingCountType;
import com.bishe.pojo.Classroom;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClassroomMapper extends BaseMapper<Classroom> {

    @Select("select id from classrooms where name=#{name}")
    Integer getIdaByName(String name);

    @Select("select name from classrooms")
    List<String> selectAllRoom();

    @Select("SELECT c.type, COUNT(b.id) AS bookings_count\n" +
            "FROM bookings b\n" +
            "JOIN classrooms c ON b.cid = c.id\n" +
            "GROUP BY c.type;\n")
    List<BookingCountType> getBookingCountType();

}
