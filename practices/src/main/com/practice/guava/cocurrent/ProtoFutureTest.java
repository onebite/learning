package com.practice.guava.cocurrent;

import java.util.concurrent.*;

/**
 * @author lxl
 * @Date 2018/4/17 16:19
 */
public class ProtoFutureTest {
    private static final ExecutorService service = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException,ExecutionException{
        Long start = System.currentTimeMillis();
        Future<Boolean> task1 = service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Thread.sleep(700);
                return true;
            }
        });

        while(true){
            if(task1.isCancelled() || task1.isDone()){
                task1.get();
                break;
            }
        }

        Future<Boolean> task2 = service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Thread.sleep(700);
                return true;
            }
        });

        while(true){
            if(task2.isCancelled() || task2.isDone()){
                task2.get();
                break;
            }
        }

    }
}
