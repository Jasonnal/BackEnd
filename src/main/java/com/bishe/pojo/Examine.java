package com.bishe.pojo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Examine {
    private Integer uid;
    private Integer cid;
    private Integer bid;
    private String username;
    private String role;
    private String name;
    private Integer status;
    private Integer startTime;
    private Integer endTime;
    private LocalDate day;
    private String comment;
    private Integer repairStatus;
    private String repairedTime;
    private String repairedComment;
    private Integer rid;
}
