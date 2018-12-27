package com.practice.concurrent.taobao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lxl
 * @Date 2018/9/5 10:07
 */
public class StressThreadWorker implements Runnable{
    private StressTask service;
    private CyclicBarrier threadStartBarrier;
    private CountDownLatch threadEndLatch;
    private AtomicInteger failedCounter = null;
    private int count;
    protected static Logger log = LoggerFactory.getLogger(SimpleResultFormater.class);
    private List<Long> everyTimes;


    public StressThreadWorker(StressContext stressContext,int count) {
        this.count = count;
        this.everyTimes = new ArrayList<>(count);
        this.service = stressContext.getStressTask();
        this.failedCounter = stressContext.getFailedCounter();
        this.threadEndLatch = stressContext.getThreadEndLatch();
        this.threadStartBarrier = stressContext.getThreadStartBarrier();
    }

    @Override
    public void run() {
        try {
            this.threadStartBarrier.await();
            this.doRun();
        } catch (Exception e) {
            log.info("test exception",e);
            e.printStackTrace();
        }
    }

    protected void doRun() throws Exception{
        for(int i=0;i < count;++i){
            long start = System.nanoTime();
            try {
                this.service.doTask();
            } catch (Exception e) {
                this.failedCounter.incrementAndGet();
            } finally {
                this.everyTimes.add(System.nanoTime() - start);
            }
        }

        this.threadEndLatch.countDown();
    }

    public List<Long> getEveryTimes() {
        return everyTimes;
    }
}
