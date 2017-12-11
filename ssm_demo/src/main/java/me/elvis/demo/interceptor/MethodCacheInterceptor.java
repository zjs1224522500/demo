package me.elvis.demo.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import me.elvis.demo.support.util.RedisUtil;

/**
 * Version:v1.0 (description:  ) Date:2017/12/11 0011  Time:15:04
 */
public class MethodCacheInterceptor implements MethodInterceptor {

	private Logger logger = LoggerFactory.getLogger(MethodCacheInterceptor.class);
	private RedisUtil redisUtil;
	private List<String> targetNamesList; // 不加入缓存的service名称
	private List<String> methodNamesList; // 不加入缓存的方法名称
	private Long defaultCacheExpireTime; // 缓存默认的过期时间
	private Long xxxRecordManagerTime; //
	private Long xxxSetRecordManagerTime; //

	/**
	 * 利用构造方法初始化读取不需要加入缓存的类名和方法名称
	 */
	public MethodCacheInterceptor() {
		try {
			// 分割字符串 这里没有加入任何方法
			String[] targetNames = {};
			String[] methodNames = {};

			// 加载过期时间设置
			defaultCacheExpireTime = 3600L;
			xxxRecordManagerTime = 60L;
			xxxSetRecordManagerTime = 60L;
			// 创建list
			targetNamesList = new ArrayList<String>(targetNames.length);
			methodNamesList = new ArrayList<String>(methodNames.length);
			Integer maxLen = targetNames.length > methodNames.length ? targetNames.length
					: methodNames.length;
			// 将不需要缓存的类名和方法名添加到list中
			for (int i = 0; i < maxLen; i++) {
				if (i < targetNames.length) {
					targetNamesList.add(targetNames[i]);
				}
				if (i < methodNames.length) {
					methodNamesList.add(methodNames[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object value = null;

		// 获取被拦截的service名称
		String targetName = invocation.getThis().getClass().getName();
		// 获取被拦截的method名称
		String methodName = invocation.getMethod().getName();

		// 不需要缓存的内容
		//if (!isAddCache(StringUtil.subStrForLastDot(targetName), methodName)) {
		if (!isAddCache(targetName, methodName)) {
			// 执行方法返回结果
			return invocation.proceed();
		}

		// 若需要缓存
		// 获取对应的参数
		Object[] arguments = invocation.getArguments();
		// 根据自定义的策略生成对应的 key
		String key = getCacheKey(targetName, methodName, arguments);
		logger.debug("*******redisKey: " + key);
		try {
			// 判断是否有缓存，缓存中若有直接返回
			if (redisUtil.exists(key)) {
				return redisUtil.get(key);
			}
			// 写入缓存
			value = invocation.proceed();

			// 写入缓存后根据service类型（字符串构造规律），新开线程进行对应的有效时间的配置
			if (value != null) {
				final String tkey = key;
				final Object tvalue = value;
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (tkey.startsWith("com.service.impl.xxxRecordManager")) {
							redisUtil.set(tkey, tvalue, xxxRecordManagerTime);
						} else if (tkey.startsWith("com.service.impl.xxxSetRecordManager")) {
							redisUtil.set(tkey, tvalue, xxxSetRecordManagerTime);
						} else {
							redisUtil.set(tkey, tvalue, defaultCacheExpireTime);
						}
					}
				}).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (value == null) {
				return invocation.proceed();
			}
		}
		return value;
	}

	/**
	 * 判断是否加入缓存
	 *
	 * @return
	 */
	private boolean isAddCache(String targetName, String methodName) {
		boolean flag = true;
		if (targetNamesList.contains(targetName)
				|| methodNamesList.contains(methodName)) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 创建缓存key（自定义的缓存key生成策略）
	 *
	 * @param targetName
	 * @param methodName
	 * @param arguments
	 */
	private String getCacheKey(String targetName, String methodName,
			Object[] arguments) {
		StringBuffer sbu = new StringBuffer();
		sbu.append(targetName).append("_").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sbu.append("_").append(arguments[i]);
			}
		}
		return sbu.toString();
	}

	/**
	 * 注入RedisUtil
	 * @param redisUtil
	 */
	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
}
