package com.practice.concurrent.taobao;

import java.util.List;

/**
 * @author lxl
 * @Date 2018/9/5 10:10
 */
public class StressResult {
    private int concurrencyLevel;
    private int totalRequests;
    private long testsTakenTime;
    private int failedRequests;
    private List<Long> allTimes;
    private List<StressThreadWorker> workers;

    public StressResult() {
    }

    public int getConcurrencyLevel() {
        return concurrencyLevel;
    }

    public void setConcurrencyLevel(int concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }

    public long getTestsTakenTime() {
        return testsTakenTime;
    }

    public void setTestsTakenTime(long testsTakenTime) {
        this.testsTakenTime = testsTakenTime;
    }

    public int getFailedRequests() {
        return failedRequests;
    }

    public void setFailedRequests(int failedRequests) {
        this.failedRequests = failedRequests;
    }

    public List<Long> getAllTimes() {
        return allTimes;
    }

    public void setAllTimes(List<Long> allTimes) {
        this.allTimes = allTimes;
    }

    public List<StressThreadWorker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<StressThreadWorker> workers) {
        this.workers = workers;
    }
}
