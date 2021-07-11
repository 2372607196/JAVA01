package com.xxx;

import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class DateUtilTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void Test()
    {
        assertEquals(true,new DateUtil().isLeapYear(-100));
    }
}
