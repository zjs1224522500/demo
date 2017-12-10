package me.elvis.demo.support.mapper;

import org.springframework.stereotype.Component;

import me.elvis.demo.support.pojo.City;

@Component
public interface CityMapper {

	boolean insertCity(City city);
}