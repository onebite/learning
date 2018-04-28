package com.practice.util;

import java.io.FileWriter;
import java.text.DecimalFormat;

/**
 * @author lxl
 * @Date 2018/4/26 14:54
 */
public class Utilities {
    public static boolean debug = false;
    public static FileWriter logFile = null;
    private static double KB = 1024.0;

    private static final String[] monthsWS = {"Jan","Feb","Mar","Apr",
            "May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    private static final String[] months = {"Jan","Feb","Mar","Apr",
            "May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    /**
     * transforms a long into a String like 4KiB
     * @param size
     * @return
     */
    public static String humanReadable(double size){
        DecimalFormat df = new DecimalFormat("###0.00");
        double f1;
        double f2;
        double f3;
        if((f1 = size / KB) > 1.0){
            if((f2 = f1/KB) > 1.0){
                if((f3 = f2/KB) > 1.0){
                    return df.format(f3) + "GiB";
                }
                return df.format(f2) + "MiB";
            }
            return df.format(f1) + "KiB";
        }

        return df.format(size) + "B";
    }


    public static String humanReadable(long size){

        return humanReadable(size * 1.0);
    }

    public static String humanReadableTime(double seconds){
        DecimalFormat df = new DecimalFormat("###0");
        double min,hour,day,sec;
        StringBuffer buf = new StringBuffer();
        sec = seconds * 1.0;
        if((min = sec/60.0) >= 1.0){
            sec = sec - Math.floor(min)*60.0;
            if((hour = min/60.0) >= 1.0){
                min = min - Math.floor(hour)*60.0;
                if((day = hour/24.0) >= 1.0){
                    buf.append(df.format(day)).append("d");
                    hour = hour - Math.floor(day)*24.0;
                }
                buf.append(df.format(hour)).append("h");
            }
            buf.append(df.format(min)).append("min");
        }
        buf.append(df.format(sec)).append("s");

        return buf.toString();
    }


    private static void reheap(String[] array,int root,int end){
        int[] stack = new int[new Double(Math.log(array.length)/Math.log(2)).intValue() + 10];
        int s = 0;
        int pos = root;
        stack[s++] = pos;
        while(2*pos <= end){
            if(2*pos + 1 > end){
                stack[s++] = 2*pos;
                break;
            }

        }
    }

}
