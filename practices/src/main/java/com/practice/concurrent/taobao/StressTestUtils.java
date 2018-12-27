package com.practice.concurrent.taobao;

import java.io.StringWriter;

/**
 * @author lxl
 * @Date 2018/9/5 14:33
 */
public class StressTestUtils {
    private static StressTester stressTester = new StressTester();
    private static SimpleResultFormater simpleResultFormater = new SimpleResultFormater();

    public StressTestUtils() {
    }

    public static StressResult test(int concurrencyLevel,int totalRequests,StressTask stressTask){

        return stressTester.test(concurrencyLevel,totalRequests,stressTask);
    }

    public static StressResult test(int concurrencyLevel,int totalRequests, StressTask stressTask, int warmUpTimes){

        return stressTester.test(concurrencyLevel,totalRequests,stressTask,warmUpTimes);
    }

    public static void testAndPrint(int concurrencyLevel,int totalRequests,StressTask stressTask){
        testAndPrint(concurrencyLevel,totalRequests,stressTask,null);
    }

    public static void testAndPrint(int concurrencyLevel, int totalRequests, StressTask stressTask, String testName) {
        StressResult stressResult = test(concurrencyLevel, totalRequests, stressTask);
        String str = format(stressResult);
        System.out.println(str);
    }

    public static String format(StressResult stressResult) {
        return format(stressResult, simpleResultFormater);
    }

    public static String format(StressResult stressResult, StressResultFormater stressResultFormater) {
        StringWriter sw = new StringWriter();
        stressResultFormater.format(stressResult, sw);
        return sw.toString();
    }
}
