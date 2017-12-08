package me.elvis.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Version:v1.0 (description:  ) Date:2017/12/8 0008  Time:16:19
 */
public class JedisDemo {

	/**
	 * Jedis单实例的测试
	 */
	@Test
	public void demo1() {
		// 1、设置IP地址和端口号
		Jedis jedis = new Jedis("45.78.12.52", 6379);
		// 2、保存数据
		jedis.set("name", "zhangjianshun");
		String value = jedis.get("name");
		System.out.println(value);
		// 4、释放资源
		jedis.close();
	}

	/**
	 * 连接池方式连接
	 */
	@Test
	public void demo2() {
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxTotal(30);
		// 设置最大等待数
		config.setMaxIdle(10);

		// 获得核心对象
		JedisPool jedisPool = new JedisPool(config, "45.78.12.52", 6379);
		Jedis jedis = null;
		try {
			// 通过连接池获得连接
			jedis = jedisPool.getResource();
			// 设置数据
			jedis.set("name", "张三");
			// 获取数据
			String value = jedis.get("name");
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedis.close();
			}
			if (null != jedisPool) {
				jedisPool.close();
			}
		}
	}

}
