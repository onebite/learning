package com.practice.lock;

import com.practice.cache.RedisManager;

/**
 * @author lxl
 * @Date 2018/4/28 16:59
 */
public class RedisLockUtil {
    private static final int defaultExpire = 60;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    public RedisLockUtil() {
    }


    /**
     *
     * 获取分布式锁
     * @param lockKey
     * @param requestId
     * @param timeout
     * @return
     */
    public static boolean tryGetDistributedLock(String lockKey,String requestId,int timeout){
        String result = RedisManager.I.set(lockKey,requestId,SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME,timeout);
        if(LOCK_SUCCESS.equals(result)){
            return true;
        }

        return false;
    }


    public static boolean releaseDistributedLock(String lokKey,String requestId){
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    }









    /**
     * 加锁
     * @param key
     * @param expire
     * @return true 加锁成功
     */
    public static boolean lock(String key,int expire){
        long status = RedisManager.I.setnx(key,"1");

        if(status == 1){
            RedisManager.I.expire(key,expire);
            return true;
        }

        return false;
    }

    public static boolean lock2(String key,int expire){
        long value = System.currentTimeMillis() + expire;
        long status = RedisManager.I.setnx(key,String.valueOf(value));

        if(status == 1){
            return true;
        }

        long oldExpireTime = Long.parseLong(RedisManager.I.get(key,String.class));
        if(oldExpireTime < System.currentTimeMillis()){
            long newExpireTime = System.currentTimeMillis() + expire;
            long currentExpireTime = Long.parseLong(RedisManager.I.getSet(key,String.valueOf(newExpireTime)));
            if(currentExpireTime == oldExpireTime){
                return true;
            }
        }

        return false;
    }

    public static boolean lock(String key){

        return lock2(key,defaultExpire);
    }

    public static void unLock(String key){
        RedisManager.I.del(key);
    }

    public static void unLock2(String key){
        long oldExpireTime = Long.parseLong(RedisManager.I.get(key,String.class));
        if(oldExpireTime > System.currentTimeMillis()){
            RedisManager.I.del(key);
        }
    }
}
