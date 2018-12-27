package com.practice.concurrent.guava;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * 减少主函数的等待时间，使得多任务能够异步非阻塞执行
 * ListenableFuture是可以监听的Future，
 * 它是对java原生Future的扩展增强。Future表示一个异步计算任务，当任务完成时可以得到计算结果。
 * 如果希望计算完成时马上就拿到结果展示给用户或者做另外的计算，就必须使用另一个线程不断的查询计算状态。
 * 这样做会使得代码复杂，且效率低下。如果使用ListenableFuture，Guava会帮助检测Future是否完成了，
 * 如果完成就自动调用回调函数，这样可以减少并发程序的复杂度
 * @author lxl
 * @Date 2018/4/17 16:39
 */
public class ListenableFutureTest {
    private final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void main(String[] args) throws Exception{
        Long start = System.currentTimeMillis();
        ListenableFuture<Boolean> task1 = service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });

        Futures.addCallback(task1, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                System.out.println("task 1 result :"+result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        ListenableFuture<Boolean> task2 = service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });

        Futures.addCallback(task2, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                System.out.println(" task 2 result :"+result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }
}
