package com.practice.concurrent.taobao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lxl
 * @Date 2018/9/5 10:46
 */
public class StressTester {
    protected static Logger log = LoggerFactory.getLogger(SimpleResultFormater.class);
    private int defaultWarmUpTimes = 1600;
    private StressTask emptyTestService = new StressTask() {
        @Override
        public Object doTask() throws Exception {
            return null;
        }
    };

    static {

    }

    protected static void warnSelf(){
        for(int i=0; i < 50; ++i){
            StressTester benchmark = new StressTester();
            benchmark.test(10,100,null,0);
        }
    }

    protected void warmUp(int warmUpTimes,StressTask stressTask){
        for(int i=0; i < warmUpTimes;++i){
            try {
                stressTask.doTask();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("test exception",e);
            }
        }
    }

    public StressResult test(int concurrencyLevel, int totalRequests, StressTask stressTask) {
        return this.test(concurrencyLevel, totalRequests, stressTask, this.defaultWarmUpTimes);
    }

    public StressResult test(int concurrencyLevel,int totalRequests,StressTask stressTask,int warmUpTimes){
        if(stressTask == null){
            stressTask = this.emptyTestService;
        }

        this.warmUp(warmUpTimes,stressTask);
        int everyThreadCount = totalRequests / concurrencyLevel;
        CyclicBarrier threadStartBarrier = new CyclicBarrier(concurrencyLevel);
        CountDownLatch threadEndLatch = new CountDownLatch(concurrencyLevel);
        AtomicInteger failedCounter = new AtomicInteger();
        StressContext stressContext = new StressContext();
        stressContext.setStressTask(stressTask);
        stressContext.setEveryThreadCount(everyThreadCount);
        stressContext.setThreadStartBarrier(threadStartBarrier);
        stressContext.setThreadEndLatch(threadEndLatch);
        stressContext.setFailedCounter(failedCounter);
        ExecutorService executorService = Executors.newFixedThreadPool(concurrencyLevel);
        List<StressThreadWorker> workers = new ArrayList<>();
        int realTotalRequests;
        StressThreadWorker worker;
        for(realTotalRequests = 0;realTotalRequests < concurrencyLevel;++realTotalRequests){
            worker = new StressThreadWorker(stressContext,everyThreadCount);
            workers.add(worker);
        }

        for(realTotalRequests = 0;realTotalRequests < concurrencyLevel;++realTotalRequests){
            worker = workers.get(realTotalRequests);
            executorService.submit(worker);
        }

        try {
            threadEndLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("interruptedException",e);
        }

        executorService.shutdownNow();
        realTotalRequests = everyThreadCount * concurrencyLevel;
        int failedRequests = failedCounter.get();
        StressResult stressResult = new StressResult();
        SortResult sortResult = getSortedTimes(workers);
        stressResult.setAllTimes(sortResult.allTimes);
        long totalTime = sortResult.threadTimes.get(sortResult.threadTimes.size() - 1).longValue();
        stressResult.setTestsTakenTime(totalTime);
        stressResult.setFailedRequests(failedRequests);
        stressResult.setTotalRequests(realTotalRequests);
        stressResult.setConcurrencyLevel(concurrencyLevel);
        stressResult.setWorkers(workers);

        return stressResult;
    }

    protected SortResult getSortedTimes(List<StressThreadWorker> workers){
        List<Long> allTimes = new ArrayList<>();
        List<Long> threadTimes = new ArrayList<>();
        Iterator iter = workers.iterator();
        while(iter.hasNext()){
            StressThreadWorker worker = (StressThreadWorker) iter.next();
            List<Long> everyWorkerTimes = worker.getEveryTimes();
            long workerTotalTime = StatisticsUtils.getTotal(everyWorkerTimes);
            threadTimes.add(workerTotalTime);
            allTimes.addAll(everyWorkerTimes);
        }
        Collections.sort(allTimes);
        Collections.sort(threadTimes);
        SortResult result = new SortResult();
        result.allTimes = allTimes;
        result.threadTimes = threadTimes;

        return result;
    }

    class SortResult {
        List<Long> allTimes;
        List<Long> threadTimes;

        SortResult() {
        }
    }
}
