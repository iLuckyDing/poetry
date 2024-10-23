package com.iashin.poetry.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;

/**
 * @projectName: poetry
 * @description: 服务缓存
 * @date: 2024/7/15 22:07
 * @author: dingzhen
 */
@Component
@Slf4j
public class PoetryCache {

    /**
     * 缓存map
     */
    private static final Map<String, Entity> MAP = new ConcurrentHashMap<>();

    /**
     * 定时器线程池，用于清除过期缓存
     */
    private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(8);

    /**
     * 缓存实体
     */
    @Getter
    @AllArgsConstructor
    private static class Entity {

        private final Object value;

        /**
         * 定时器future
         */
        private final ScheduledFuture<?> future;
    }

    /**
     * 添加缓存
     * @param key  键
     * @param data 值
     */
    public static void put(String key, Object data) {
        put(key, data, 0);
    }

    /**
     * 添加缓存
     * @param key 键
     * @param data 值
     * @param expire 过期时间,单位秒
     */
    public static void put(String key, Object data, long expire) {
        // 清除原键值对
        Entity entity = MAP.get(key);
        if (entity != null) {
            ScheduledFuture<?> future = entity.getFuture();
            if (future != null) {
                future.cancel(true);
            }
        }

        // 设置过期时间
        if (expire > 0) {
            ScheduledFuture<?> schedule = EXECUTOR.schedule(() -> {
                MAP.remove(key);
            }, expire, TimeUnit.SECONDS);
            MAP.put(key, new Entity(data, schedule));
        } else {
            //不设置过期时间
            MAP.put(key, new Entity(data, null));
        }
    }

    /**
     * 读取缓存
     * @param key 键
     * @return 缓存数据
     */
    public static Object get(String key) {
        Entity entity = MAP.get(key);
        return entity == null ? null : entity.getValue();
    }

    /**
     * 读取所有缓存
     * @return 缓存数据集合
     */
    public static Collection<?> values() {
        return MAP.values();
    }

    /**
     * 清除缓存
     * @param key 该缓存的键
     * @return 清除掉的缓存数据
     */
    public static Object remove(String key) {
        //清除原缓存数据
        Entity entity = MAP.remove(key);
        if (entity == null) {
            return null;
        }
        //清除原键值对定时器
        ScheduledFuture<?> future = entity.getFuture();
        if (future != null) {
            future.cancel(true);
        }
        return entity.getValue();
    }

    /**
     * 查询当前缓存的数量
     * @return 缓存数量
     */
    public static int size() {
        return MAP.size();
    }

}
