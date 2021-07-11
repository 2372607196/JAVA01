package com.xxx;

public class DateUtil {
    boolean isLeapYear(int year){
        if(year>0&&year<=10000){
            if(0!=year%100&&0==year%4){
                return true;
            }
            else if(0==year%100&&0==year%400){
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
}
