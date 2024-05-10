package com.bishe.pojo;

import lombok.Data;

@Data
public class HourlyBookingDTO {
    private int hourOfDay;
    private int bookingCount;
    private String hourRange;

}
