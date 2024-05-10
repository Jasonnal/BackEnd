package com.bishe.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishe.mapper.BookingMapper;
import com.bishe.pojo.*;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookingService extends ServiceImpl<BookingMapper, Booking> {

    @Resource
    BookingMapper bookingMapper;

    public List<Booking> findBookingByDateAndRoomName(String name, LocalDate day){
        List<Booking> bookingByDateAndRoomName = bookingMapper.findBookingByDateAndRoomName(name, day);
        if (bookingByDateAndRoomName == null) {
            return new ArrayList<>();
        }
        return bookingByDateAndRoomName;
    }
    public Page<Examine> selectAllReserve(Page<Examine> page){
        Page<Examine> examinePage = bookingMapper.selectAllReserve(page);
        return examinePage;

    }
    public int updateStatusByUidCidStartTimeAndDay(Integer uid, Integer cid, Integer startTime, LocalDate day, Integer status){
        return bookingMapper.updateStatusByUidCidStartTimeAndDay(uid, cid, startTime, day, status);
    }

    public Page<Examine> getMyApply(Integer uid, Page<Examine> page) {
        Page<Examine> myApply = bookingMapper.getMyApply(uid, page);
        return myApply;
    }

    public int getApplyCount() {
        return bookingMapper.getApplyCount();
    }

    public int getFreeRoomCount() {
        return bookingMapper.getFreeRoomCount();
    }

    public List<TimeReservation> getTimeReserve() {
        return bookingMapper.getTimeReserve();
    }

    public int pieSelect(String name) {
        return bookingMapper.pieSelect(name);
    }

    public List<TimeLine> lineSelect(String name){
        return bookingMapper.lineSelect(name);
    }

    public List<Map<String, Object>> getBookingCountsByWeekday() {
        return bookingMapper.getBookingCountsByWeekday();
    }

    public List<Map<String,Object>> getBookingCountByMonth(){
        return bookingMapper.getBookingCountsByMonth();
    }

    public List<HourlyBookingDTO> getBookingCountHour(){
        return bookingMapper.getBookingHourCount();
    }
}
