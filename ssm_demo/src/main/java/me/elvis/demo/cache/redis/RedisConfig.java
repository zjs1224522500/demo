package me.elvis.demo.cache.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

/**
 * Version:v1.0 (description: 使用程序进行Spring-Redis的整合  )
 * Date:2017/12/8 0008  Time:17:21
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	public JedisConnectionFactory getRedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();

		jedisConnectionFactory.setHostName("127.0.0.1");
		jedisConnectionFactory.setPort(6379);
		jedisConnectionFactory.setPassword("root");
		return jedisConnectionFactory;
	}

	/**
	 * 为template绑定factory
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<String,String> getRedisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String,String> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		return template;
	}

	@Bean
	public CacheManager getCacheManager(RedisTemplate template) {
		RedisCacheManager manager = new RedisCacheManager(template);
		manager.setDefaultExpiration(3000);
		return manager;
	}

	/**
	 * 自定义Redis主键生成策略
	 * @return
	 */
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {

			@Override
			public Object generate(Object o, Method method, Object... objects) {
				StringBuilder sb = new StringBuilder();
				sb.append(o.getClass().getName());
				sb.append(method.getName());
				for (Object obj : objects) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
}
