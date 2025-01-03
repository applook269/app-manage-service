package com.hooke.zdl.admin.module.support.redis;

import com.hooke.zdl.admin.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RIdGenerator;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redisson 业务
 */
@Slf4j
@Service
public class RedissonService {

    @Autowired
    private final RedissonClient redissonClient;

    public RedissonService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    /**
     * 获取锁 并 执行程序
     */
    public <T> T executeWithLock(String lockKey, long waitTime, long lockTime, Supplier<T> supplier) {
        // 获取锁
        RLock lock = this.tryLock(lockKey, waitTime, lockTime);
        try {
            return supplier.get();
        } finally {
            // 释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 获取锁 并 执行程序
     */
    public void executeWithLock(String lockKey, long waitTime, long lockTime, Runnable runnable) {
        // 获取锁
        RLock lock = this.tryLock(lockKey, waitTime, lockTime);
        try {
            runnable.run();
        } finally {
            // 释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 尝试获取锁
     * 最多等待 waitTime 毫秒
     * 获取锁成功后占用 lockTime 毫秒
     * ps:需要手动解锁 lock.unlock()
     */
    public RLock tryLock(String lockKey, long waitTime, long lockTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean getLock = lock.tryLock(waitTime, lockTime, TimeUnit.MILLISECONDS);
            if (getLock) {
                return lock;
            }
        } catch (InterruptedException e) {
            log.error("Redisson tryLock", e);
        }
        throw new BusinessException("业务繁忙,请稍后重试~");
    }

    /**
     * 获取 id 生成器
     * nextId 可生成连续不重复的id
     */
    public RIdGenerator idGenerator(String key) {
        return redissonClient.getIdGenerator(key);
    }

    /**
     * 存放任意数据类型
     */
    public <T> void putObj(String key, T v, Duration duration) {
        redissonClient.getBucket(key).set(v, duration);
    }

    /**
     * 获取任意数据类型
     */
    public <T> T getObj(String key, Class<T> clazz) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

}
