package me.elvis.demo.service.impl;

import org.junit.Test;

import me.elvis.demo.service.CityService;
import me.elvis.demo.support.mapper.CityMapper;
import me.elvis.demo.support.pojo.City;

import static org.junit.Assert.*;

/**
 * Version:v1.0 (description:  ) Date:2017/12/8 0008  Time:17:05
 */
public class CityServiceImplTest extends BaseTest {

	private CityService cityService;

	City city = new City();

	{
		cityService = getBean(CityService.class);

		city.setCity("成都");
		city.setCityCode("0028");
		city.setCityLevel((byte) 1);
		city.setCityName("成都");
		city.setProvince("四川省");
		city.setProvinceCode("028");
		city.setState("1");

	}

	@Test
	public void addCity() throws Exception {
		System.out.println(cityService.addCity(city));
	}

}