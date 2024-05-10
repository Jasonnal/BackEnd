package com.bishe.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("repair")
public class Repair {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String comment;
    private String repairedTime;
    private Integer repairStatus;
    private Integer bid;
}
