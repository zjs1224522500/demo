package me.elvis.demo.service;

import java.util.List;

import me.elvis.demo.support.pojo.City;

/**
 * Version:v1.0 (description:  ) Date:2017/12/8 0008  Time:17:00
 */
public interface CityService {

	boolean addCity(City city);

	List<City> getCitiesByName(String cityName);
}
