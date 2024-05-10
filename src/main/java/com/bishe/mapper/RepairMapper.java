package com.bishe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bishe.pojo.Examine;
import com.bishe.pojo.Repair;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RepairMapper extends BaseMapper<Repair> {

    @Select({
            "SELECT",
            "    u.id AS uid,",
            "    b.cid,",
            "    b.id AS bid,",
            "    u.username,",
            "    u.role,",
            "    c.name,",
            "    b.status,",
            "    b.start_time AS startTime,",
            "    b.end_time AS endTime,",
            "    b.day,",
            "    b.comment,",
            "    COALESCE(r.repair_status,0) AS repairStatus ,r.repaired_time As repairedTime, r.comment As repairedComment",
            "FROM",
            "    bookings b",
            "JOIN",
            "    user u ON b.uid = u.id",
            "JOIN",
            "    classrooms c ON b.cid = c.id",
            "LEFT JOIN",
            "    repair r ON b.id = r.bid",
            "WHERE",
            "    u.id = #{uid} AND (r.repair_status = 1 OR r.repair_status = 2)",
            "ORDER BY",
            "    r.id DESC"
    })
    Page<Examine> getMyApply(@Param("uid") Integer uid, Page<Examine> page);
    @Select({
            "SELECT",
            "    u.id AS uid,",
            "    b.cid,",
            "    b.id AS bid,",
            "    u.username,",
            "    u.role,",
            "    c.name,",
            "    b.status,",
            "    b.start_time AS startTime,",
            "    b.end_time AS endTime,",
            "    b.day,",
            "    b.comment,",
            "    COALESCE(r.repair_status, 0) AS repairStatus ,r.repaired_time As repairedTime, r.comment As repairedComment, r.id As rid",
            "FROM",
            "    bookings b",
            "JOIN",
            "    user u ON b.uid = u.id",
            "JOIN",
            "    classrooms c ON b.cid = c.id",
            "LEFT JOIN",
            "    repair r ON b.id = r.bid",
            "WHERE",
            "    (r.repair_status = 1 OR r.repair_status = 2)",
            "ORDER BY",
            "    b.created_at DESC"
    })
    Page<Examine> getAllApply(Page<Examine> page);


    @Update("UPDATE bookings \n" +
            "SET `status` = 0 \n" +
            "WHERE\n" +
            "\tcid = #{cid} AND `day` > CURRENT_DATE AND (`status`=1 OR `status`=2)")
    Boolean repairProcess(Integer cid);
}
