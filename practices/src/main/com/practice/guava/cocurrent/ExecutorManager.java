package com.practice.guava.cocurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author lxl
 * @Date 2018/4/18 14:45
 */
public class ExecutorManager {
    public final static ExecutorManager INSTANCE = new ExecutorManager();
    private ThreadPoolExecutor executor;

    public ThreadPoolExecutor getExecutor() {
        if(executor == null || executor.isShutdown() || executor.isTerminated()){
            ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("pool_%d").setDaemon(true).build();
            executor = new ThreadPoolExecutor(100,100 * 4,
                    30, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1024),factory);

        }
        return executor;
    }

}
