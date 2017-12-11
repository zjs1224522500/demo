package me.elvis.jedis;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Version:v1.0 (description:  ) Date:2017/12/8 0008  Time:16:19
 */
public class JedisDemo {

	@Test
	public void getAllKeysValues() {
		long start = System.currentTimeMillis();

		//连接redis服务器，localhost:6379
		Jedis redis = new Jedis("localhost", 6379);
		redis.auth("root");
		// 获取所有key
		Set<byte[]> keySet = redis.keys("*".getBytes());
		byte[][] keys = keySet.toArray(new byte[keySet.size()][]);
		// 获取所有value
		byte[][] values = redis.mget(keys).toArray(new byte[keySet.size()][]);

		// 打印key-value对
		for (int i = 0; i < keySet.size(); ++i) {
			System.out.println(byte2string(keys[i]) + " --- " + byte2string(values[i]));
		}

		long end = System.currentTimeMillis();
		// 计算耗时
		System.out.println("Query " + values.length + " pairs takes " + (end - start) + " millis");
		redis.close();
	}

	private static String byte2hex(byte[] buffer) {
		String h = "0x";

		for (byte aBuffer : buffer) {
			String temp = Integer.toHexString(aBuffer & 0xFF);
			if (temp.length() == 1) {
				temp = "0" + temp;
			}
			h = h + " " + temp;
		}
		return h;
	}

	private static String byte2string(byte[] buffer) {
		try {
			return new String(buffer,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


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
