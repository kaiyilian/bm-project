package com.bumu.arya.admin;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author majun
 * @date 2017/3/14
 */
public class ObjectsTest {

	private static Logger logger = LoggerFactory.getLogger(ObjectsTest.class);

	/**
	 * 使用反射创建Objects
	 */
	@Test
	public void newObjects() throws Exception {
		Class<Objects> entityClass = Objects.class;
		Constructor<Objects> constructor = entityClass.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
		// No java.util.Objects instances for you!
	}


	@Test
	public void objectsEquals() {
		Assert.assertTrue(Objects.equals(1, 1));
		Assert.assertTrue(Objects.equals("1", "1"));
		Assert.assertFalse(Objects.equals("1", "2"));
	}

	@Test
	public void objectsDeepEquals() {

		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");

		List<String> other = new ArrayList<>();
		other.add("1");
		other.add("2");

		Assert.assertTrue(Objects.deepEquals(list, other));
	}

	@Test
	public void objectsToStringNullDefault() {
		Assert.assertEquals(Objects.toString(null, "this is a null value"), "this is a null value");
	}

	@Test
	public void requireNonNullSupplier(){
		Objects.requireNonNull(null, new Supplier<String>() {
			@Override
			public String get() {
				return "null value";
			}
		});
	}
}
