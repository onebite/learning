package com.practice.concurrent.guava;

import com.google.common.util.concurrent.Monitor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过Monitor的Guard进行条件阻塞
 * @author lxl
 * @Date 2018/4/17 15:28
 */
public class MonitorExample {
    private final AtomicInteger runningNum = new AtomicInteger();
    private static final int max_times = 1000;
    private Monitor monitor = new Monitor();

    private Monitor.Guard belowTimes = new Monitor.Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return runningNum.get() < max_times;
        }
    };

    public void doWork(Object param) throws InterruptedException{
        //Guard(形如Condition)，不满足则阻塞，而且我们并没有在Guard进行任何通知操作
        //Monitor就像java本土的synchronized, ReentrantLock一样，每次只运行一个线程占用，且可重占用，每一次占用会对应一次退出占用
        monitor.enterWhen(belowTimes);
        try{
            runningNum.incrementAndGet();
            //do something
        }finally {
            runningNum.decrementAndGet();
            monitor.leave();
        }
    }
}
