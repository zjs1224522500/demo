package me.elvis.demo.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import me.elvis.demo.service.CityService;
import me.elvis.demo.support.mapper.CityMapper;
import me.elvis.demo.support.pojo.City;

/**
 * Version:v1.0 (description:  ) Date:2017/12/8 0008  Time:17:00
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CityServiceImpl implements CityService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private CityMapper cityMapper;

	@Override
	public boolean addCity(City city) {
		logger.info("*******before insert the city****");
		boolean status = cityMapper.insertCity(city);
		logger.info("*******after insert the city*****");
		return status;
	}

	@Override
	public List<City> getCitiesByName(String cityName) {
		logger.info("***********before select the city******");
		List<City> cities = cityMapper.searchCities(null, null, null, null, cityName, null, null);
		logger.info("***********after select the city*******");
		return cities;
	}
}
