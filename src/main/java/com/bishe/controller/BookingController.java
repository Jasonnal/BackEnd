package com.bishe.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bishe.common.AuthAccess;
import com.bishe.common.Result;
import com.bishe.pojo.*;
import com.bishe.service.BookingService;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookingController {
    @Resource
    BookingService bookingService;



    @GetMapping("/has")
    public Result findByNameAndDay(@RequestParam String roomName, @RequestParam LocalDate day){

        List<Booking> bookings = bookingService.findBookingByDateAndRoomName(roomName,day);
        return Result.success(bookings);
    }



    @PostMapping("/reserve")
    public Result Reservation(@RequestBody Booking booking){
        boolean save = bookingService.save(booking);
        System.out.println(booking.getDay());
        return Result.success(save);
    }


    @GetMapping("/selectAll")
    public Result selectAllReserve(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize){
        Page<Examine> page = new Page<>(pageNum, pageSize);
        Page<Examine> examinePage = bookingService.selectAllReserve(page);
        return Result.success(examinePage);
    }

    @PutMapping("/update")
    public Result updateStatus(@RequestBody Booking booking){
        int rowsAffected = bookingService.updateStatusByUidCidStartTimeAndDay(
                booking.getUid(), booking.getCid(), booking.getStartTime(), booking.getDay(), booking.getStatus());
        if (rowsAffected > 0) {
            return Result.success("审批成功");
        } else {
            return Result.error("审批失败");
        }
    }


    @GetMapping("/selectApply")
    public Result getMyApply(@RequestParam Integer uid,@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        Page<Examine> page = new Page<>(pageNum,pageSize);
        Page<Examine> myApply = bookingService.getMyApply(uid, page);
        return Result.success(myApply);
    }

    @GetMapping("/applyCount")
    public Result getApplyCount(){
        int applyCount = bookingService.getApplyCount();
        return Result.success(applyCount);
    }

    @GetMapping("/freeRoomCount")
    public Result getFreeRoomCount(){
        int freeRoomCount = bookingService.getFreeRoomCount();
        return Result.success(freeRoomCount);
    }

    @GetMapping("/timereserve")
    public Result timeReserve(){
        List<TimeReservation> timeReserve = bookingService.getTimeReserve();
        return Result.success(timeReserve);
    }

    //饼图的查询
    @GetMapping("/selectPie")
    public Result pieSelect(@RequestParam String name){
        int pieSelect = bookingService.pieSelect(name);
        return Result.success(pieSelect);
    }
    //折线图代码
    @GetMapping("/selectLine")
    public Result lineSelect(@RequestParam String name){
        List<TimeLine> timeLines = bookingService.lineSelect(name);
        return Result.success(timeLines);
    }


    @GetMapping("/countByWeek")
    public Result getBookingCountsByWeekday(){
        List<Map<String, Object>> bookingCountsByWeekday = bookingService.getBookingCountsByWeekday();
        return Result.success(bookingCountsByWeekday);
    }
    @GetMapping("/countByMonth")
    public Result getBookingCountsByMonth(){
        List<Map<String, Object>> bookingCountsByMonth = bookingService.getBookingCountByMonth();
        return Result.success(bookingCountsByMonth);
    }
    @GetMapping("/countByHour")
    public Result getBookingCountsByHour(){
        List<HourlyBookingDTO> bookingCountHour = bookingService.getBookingCountHour();
        return Result.success(bookingCountHour);
    }


}
