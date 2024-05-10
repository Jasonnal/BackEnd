package com.bishe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bishe.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingMapper extends BaseMapper<Booking> {

    //TODO 我要加对status的判断，标红的应该是已经被预约status=1
    @Select("SELECT * FROM bookings " +
            "JOIN classrooms ON bookings.cid = classrooms.id " +
            "WHERE status=2 AND day = #{day} AND classrooms.name = #{name}")
    List<Booking> findBookingByDateAndRoomName(String name, LocalDate day);

    @Select("SELECT u.id AS uid, b.cid, u.username, u.role, c.name, b.status, b.start_time AS startTime, b.end_time AS endTime, b.day, b.comment  " +
            "FROM bookings b " +
            "JOIN user u ON b.uid = u.id " +
            "JOIN classrooms c ON b.cid = c.id " +
            "WHERE ((b.day = CURDATE() AND b.start_time >= HOUR(NOW())) OR b.day > CURDATE()) AND b.status = 1 order by b.updated_at DESC ")
    Page<Examine> selectAllReserve(Page<Examine> page);

    @Update("UPDATE bookings SET status = #{status} " +
            "WHERE uid = #{uid} AND cid = #{cid} AND start_time = #{startTime} AND day = #{day}")
    int updateStatusByUidCidStartTimeAndDay(Integer uid,
                                            Integer cid,
                                            Integer startTime,
                                            LocalDate day,
                                            Integer status);

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
            "    u.id = #{uid}",
            "ORDER BY",
            "    b.created_at DESC"
    })
    Page<Examine> getMyApply(@Param("uid") Integer uid, Page<Examine> page);

    @Select("SELECT COUNT(*) FROM bookings WHERE day = CURDATE()")
    int getApplyCount();

    @Select("SELECT COUNT(*) AS free_rooms_count " +
            "FROM classrooms " +
            "WHERE id NOT IN ( " +
            "    SELECT DISTINCT cid " +
            "    FROM bookings " +
            "    WHERE day = CURDATE() " +
            "    AND start_time = HOUR(NOW()) " +
            "    AND status = 2 " +
            ");")
    int getFreeRoomCount();

    @Select("SELECT c.id AS cid, c.name , IFNULL(b.status, 5) AS status " +
            "FROM classrooms c " +
            "LEFT JOIN bookings b ON c.id = b.cid " +
            "AND b.day = CURDATE() " +
            "AND b.start_time = HOUR(NOW()) " +
            "ORDER BY c.id ")
    List<TimeReservation> getTimeReserve();

    @Select("SELECT COUNT(*) AS `booking_count`" +
            "FROM `bookings`" +
            "INNER JOIN `classrooms` ON `bookings`.`cid` = `classrooms`.`id`" +
            "WHERE `classrooms`.`name` = #{name}" +
            "AND DATE(`bookings`.`day`) = CURDATE()" +
            "AND `bookings`.`status` = 2;")
    int pieSelect(String name);

    @Select(" SELECT   \n" +
            "    DATE_SUB(DATE_SUB(CURDATE(), INTERVAL 1 DAY), INTERVAL days.day DAY) AS booking_day,  \n" +
            "    COALESCE(COUNT(b.id), 0) AS booking_count  \n" +
            "FROM (  \n" +
            "    SELECT 0 AS day  \n" +
            "    UNION ALL SELECT 1  \n" +
            "    UNION ALL SELECT 2  \n" +
            "    UNION ALL SELECT 3  \n" +
            "    UNION ALL SELECT 4  \n" +
            "    UNION ALL SELECT 5  \n" +
            "    UNION ALL SELECT 6  \n" +
            ") AS days  \n" +
            "LEFT JOIN bookings b ON DATE(b.day) = DATE_SUB(DATE_SUB(CURDATE(), INTERVAL 1 DAY), INTERVAL days.day DAY)  \n" +
            "                      AND b.cid = (  \n" +
            "                          SELECT id   \n" +
            "                          FROM classrooms   \n" +
            "                          WHERE name = #{name}  \n" +
            "                      )  \n" +
            "GROUP BY days.day  \n" +
            "ORDER BY days.day DESC;\n ")
    List<TimeLine> lineSelect(String name);

    @Select("SELECT DAYNAME(b.day) AS weekday, COUNT(b.id) AS bookingsCount " +
            "FROM bookings b " +
            "GROUP BY weekday " +
            "ORDER BY FIELD(weekday, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday')")
    List<Map<String, Object>> getBookingCountsByWeekday();

    @Select("SELECT months.month_name AS month, COALESCE(bookingsCount, 0) AS bookingsCount " +
            "FROM ( " +
            "   SELECT 'January' AS month_name UNION ALL SELECT 'February' UNION ALL SELECT 'March' UNION ALL " +
            "   SELECT 'April' UNION ALL SELECT 'May' UNION ALL SELECT 'June' UNION ALL SELECT 'July' UNION ALL " +
            "   SELECT 'August' UNION ALL SELECT 'September' UNION ALL SELECT 'October' UNION ALL SELECT 'November' UNION ALL SELECT 'December' " +
            ") months " +
            "LEFT JOIN ( " +
            "   SELECT MONTHNAME(b.day) AS month, COUNT(b.id) AS bookingsCount " +
            "   FROM bookings b " +
            "   GROUP BY month " +
            ") booking_counts ON months.month_name = booking_counts.month " +
            "ORDER BY FIELD(months.month_name, 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December')")
    List<Map<String, Object>> getBookingCountsByMonth();


    @Select("SELECT\n" +
            "    hours.hour_of_day,\n" +
            "    IFNULL( booking_count, 0 ) AS booking_count,\n" +
            "    CONCAT( LPAD( hours.hour_of_day, 2, '0' ), ':00-', LPAD( hours.hour_of_day + 1, 2, '0' ), ':00' ) AS hour_range \n" +
            "FROM\n" +
            "    (\n" +
            "    SELECT\n" +
            "        0 AS hour_of_day UNION ALL\n" +
            "    SELECT\n" +
            "        1 UNION ALL\n" +
            "    SELECT\n" +
            "        2 UNION ALL\n" +
            "    SELECT\n" +
            "        3 UNION ALL\n" +
            "    SELECT\n" +
            "        4 UNION ALL\n" +
            "    SELECT\n" +
            "        5 UNION ALL\n" +
            "    SELECT\n" +
            "        6 UNION ALL\n" +
            "    SELECT\n" +
            "        7 UNION ALL\n" +
            "    SELECT\n" +
            "        8 UNION ALL\n" +
            "    SELECT\n" +
            "        9 UNION ALL\n" +
            "    SELECT\n" +
            "        10 UNION ALL\n" +
            "    SELECT\n" +
            "        11 UNION ALL\n" +
            "    SELECT\n" +
            "        12 UNION ALL\n" +
            "    SELECT\n" +
            "        13 UNION ALL\n" +
            "    SELECT\n" +
            "        14 UNION ALL\n" +
            "    SELECT\n" +
            "        15 UNION ALL\n" +
            "    SELECT\n" +
            "        16 UNION ALL\n" +
            "    SELECT\n" +
            "        17 UNION ALL\n" +
            "    SELECT\n" +
            "        18 UNION ALL\n" +
            "    SELECT\n" +
            "        19 UNION ALL\n" +
            "    SELECT\n" +
            "        20 UNION ALL\n" +
            "    SELECT\n" +
            "        21 UNION ALL\n" +
            "    SELECT\n" +
            "        22 UNION ALL\n" +
            "    SELECT\n" +
            "        23 \n" +
            "    ) AS hours\n" +
            "    LEFT JOIN (\n" +
            "    SELECT HOUR\n" +
            "        ( created_at ) AS hour_of_day,\n" +
            "        COUNT(*) AS booking_count \n" +
            "    FROM\n" +
            "        bookings \n" +
            "    GROUP BY\n" +
            "    HOUR ( created_at )) AS counts ON hours.hour_of_day = counts.hour_of_day \n" +
            "ORDER BY\n" +
            "    hours.hour_of_day;\n")
    List<HourlyBookingDTO> getBookingHourCount();





}
