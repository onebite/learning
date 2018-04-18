package com.practice.guava.cocurrent;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.practice.util.KeyUtils;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 接口防重复频繁请求
 * @author lxl
 * @Date 2018/4/17 17:28
 */
public class CacheBuilderTest {
    private static final Cache<Long,Boolean> cache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.SECONDS).build();
    public static void main(String[] args) throws Exception{
        String request = "sfljsjl jslj ljsflsjlfueoiurlsjdfluso lsjlfusoudfos jlsjflsuofsjfl";
        String[] params = new String[]{"5454","4645","fsfsjl","sfjsjl 4646","sfjsljfljx"};
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        Random random = new Random();
        for(int i=0;i < 10000;i++){
            String rq = request.substring(random.nextInt(request.length())) + params[random.nextInt(params.length)];
            for (int j=0;j < 4;j++) {
                ListenableFuture<Boolean> future = service.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        try {
                            request(rq);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                });

                future.addListener(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(rq+" done");
                    }
                },service);
            }
        }
    }

    public static void request(String request) throws ExecutionException{
        cache.get(KeyUtils.hash(request), new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "" + request);

                return true;
            }
        });
    }
}
