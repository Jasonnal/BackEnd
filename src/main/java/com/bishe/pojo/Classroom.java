package com.bishe.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("classrooms")
public class Classroom {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer capacity;
    private String type;
    private String location;
    private String description;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean open;


}
