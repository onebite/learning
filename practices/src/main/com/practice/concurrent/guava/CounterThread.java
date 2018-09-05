package com.practice.concurrent.guava;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lxl
 * @Date 2018/4/18 15:13
 */
public class CounterThread implements Runnable {
    private static int counter = 0;
    private static String str = new String("lock");
    //偏向锁
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        lock.lock();
        System.out.println(counter++);
        lock.unlock();

        //重量级锁
//        synchronized (str.intern()){
//            System.out.println(counter++);
//        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
