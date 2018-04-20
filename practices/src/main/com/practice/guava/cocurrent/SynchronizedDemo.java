package com.practice.guava.cocurrent;

import java.util.concurrent.ExecutorService;

/**
 * @author lxl
 * @Date 2018/4/18 11:49
 */
public class SynchronizedDemo {

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = ExecutorManager.INSTANCE.getExecutor();
        for(int i=0;i<1000;i++){
            executorService.submit(new CounterThread());
        }

        Thread.sleep(60000);
    }

}
