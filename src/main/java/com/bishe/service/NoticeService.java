package com.bishe.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishe.mapper.NoticeMapper;
import com.bishe.pojo.Notice;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {

    @Resource
    private NoticeMapper noticeMapper;
}
