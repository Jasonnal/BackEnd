package com.bishe.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@TableName("bookings")
public class Booking {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private Integer cid;
    private Integer startTime;
    private Integer endTime;
    private LocalDate day;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comment;
}
