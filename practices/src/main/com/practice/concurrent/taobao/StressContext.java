package com.practice.concurrent.taobao;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lxl
 * @Date 2018/9/5 10:37
 */
public class StressContext {
    private int everyThreadCount;
    private CyclicBarrier threadStartBarrier;
    private CountDownLatch threadEndLatch;
    private AtomicInteger failedCounter;
    private StressTask stressTask;

    public StressContext() {
    }

    public int getEveryThreadCount() {
        return everyThreadCount;
    }

    public void setEveryThreadCount(int everyThreadCount) {
        this.everyThreadCount = everyThreadCount;
    }

    public CyclicBarrier getThreadStartBarrier() {
        return threadStartBarrier;
    }

    public void setThreadStartBarrier(CyclicBarrier threadStartBarrier) {
        this.threadStartBarrier = threadStartBarrier;
    }

    public CountDownLatch getThreadEndLatch() {
        return threadEndLatch;
    }

    public void setThreadEndLatch(CountDownLatch threadEndLatch) {
        this.threadEndLatch = threadEndLatch;
    }

    public AtomicInteger getFailedCounter() {
        return failedCounter;
    }

    public void setFailedCounter(AtomicInteger failedCounter) {
        this.failedCounter = failedCounter;
    }

    public StressTask getStressTask() {
        return stressTask;
    }

    public void setStressTask(StressTask stressTask) {
        this.stressTask = stressTask;
    }
}
