package me.elvis.util.entity;

import org.apache.catalina.util.StringManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Version:v1.0 (description:  ) Date:2017/10/26 0026  Time:15:52
 */
public final class ParameterMap extends HashMap {

	public ParameterMap() {
		super();
	}

	public ParameterMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public ParameterMap(Map map) {

	}

	private boolean locked = false;

	public boolean isLocked() {
		return this.locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void throwException() {
		if (isLocked()) {
			throw new IllegalStateException(sm.getString("parameterMap.locked"));
		}
	}

	private static final StringManager sm = StringManager.getManager("me.elvis.util.entity");

	public void clear() {
		throwException();
		super.clear();
	}

	public Object put(Object key, Object value) {
		throwException();
		return super.put(key, value);
	}

	public void putAll(Map map) {
		throwException();
		super.putAll(map);
	}

	public Object remove(Object key) {
		throwException();
		return super.remove(key);
	}
}
