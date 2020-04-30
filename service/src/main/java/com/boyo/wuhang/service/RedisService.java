package com.boyo.wuhang.service;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 持续时间
	 */
	private static final long TLL = 4 * 60 * 60;


	/**
	 * 将 key，value 存放到redis数据库中，默认设置
	 *
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, JSONObject.fromObject(value).toString(), TLL, TimeUnit.SECONDS);
	}

	/**
	 * 将 key，value 存放到redis数据库中，设置过期时间单位是秒
	 *
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public void set(String key, Object value, long expireTime) {
		redisTemplate.opsForValue().set(key, JSONObject.fromObject(value).toString(), expireTime, TimeUnit.SECONDS);
	}

	/**
	 * 判断 key 是否在 redis 数据库中
	 *
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

//    /**
//     * 获取与 key 对应的对象
//     * @param key
//     * @param clazz 目标对象类型
//     * @param <T>
//     * @return
//     */
//    public <T> T get(String key, Class<T> clazz) {
//        String s = get(key);
//        if (s == null) {
//            return null;
//        }
//        return  JSONObject.toBean(JSONObject.fromObject(s).toString(),clazz);
//    }

	/**
	 * 获取 key 对应的字符串
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 删除 key 对应的 value
	 *
	 * @param key
	 */
	public void delete(String key) {
		redisTemplate.delete(key);
	}
}
