package me.elvis.demo.support.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

import me.elvis.demo.support.pojo.City;

@Component
public interface CityMapper {

	boolean insertCity(City city);

	List<City> searchCities(
			@Param("cityId")
					Integer cityId,
			@Param("cityCode")
					String cityCode,
			@Param("cityLevel")
					String cityLevel,
			@Param("provinceCode")
					String provinceCode,
			@Param("cityName")
					String cityName,
			@Param("city")
					String city,
			@Param("province")
					String province);
}