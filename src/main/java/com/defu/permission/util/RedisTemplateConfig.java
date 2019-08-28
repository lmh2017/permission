package com.defu.permission.util;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * author: flchen
 * Date: 2019-08-02
 * Time: 08:59
 *
 * @Description:
 **/
@Configuration
public class RedisTemplateConfig<T> {

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	@Bean
	public RedisTemplate<String, T> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<String, T> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new FastJsonRedisSerializer(Object.class));
		return template;
	}
}
