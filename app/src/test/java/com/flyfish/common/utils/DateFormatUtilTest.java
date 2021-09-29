package com.flyfish.common.utils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateFormatUtilTest {

    @Test
    public void format() {
        long timestamp = 1632927787975L;
        Date date = new Date(timestamp);
        String formattedDate = DateFormatUtil.format(date, "yyyyMMdd hh:mm:ss");
        assertEquals("20210929 11:03:07", formattedDate);

        formattedDate = DateFormatUtil.format(date, "YYMMdd HH:mm:ss");
        assertEquals("210929 23:03:07", formattedDate);

        formattedDate = DateFormatUtil.format(date, "YYYY-MM-dd HH:mm:ss");
        assertEquals("2021-09-29 23:03:07", formattedDate);

        formattedDate = DateFormatUtil.format(date, "yy-MMM-dd HH:mm:ss D");
        assertEquals("21-九月-29 23:03:07 272", formattedDate);
    }

    @Test
    public void testFormat() {
        long timestamp = 1632927787975L;
        String formattedDate = DateFormatUtil.format(timestamp, "yyyyMMdd hh:mm:ss");
        assertEquals("20210929 11:03:07", formattedDate);

        formattedDate = DateFormatUtil.format(timestamp, "yyyyMMdd HH:mm:ss");
        assertEquals("20210929 23:03:07", formattedDate);

        formattedDate = DateFormatUtil.format(timestamp, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2021-09-29 23:03:07", formattedDate);

        formattedDate = DateFormatUtil.format(timestamp, "yyyy-MM-dd HH:mm:ss.sss");
        assertEquals("2021-09-29 23:03:07.007", formattedDate);
    }
}