package me.elvis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import me.elvis.demo.service.CityService;
import me.elvis.demo.support.pojo.City;

/**
 * Version:v1.0 (description:  ) Date:2017/12/9 0009  Time:14:04
 */
@Controller
@RequestMapping("/city")
public class CityController {

	@Autowired
	private CityService cityService;

	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {

		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CityController.java **********</div><br><br>";
		return new ModelAndView("welcome", "message", message);
	}

	@RequestMapping("/province/cities")
	@ResponseBody
	public Object provinceCities() {

		List<City> list = new ArrayList<City>();
		City city1 = new City();
		city1.setCity("济南市");
		city1.setCityCode("4510");
		city1.setCityLevel((byte) 1);
		city1.setCityName("济南市");
		city1.setProvince("山东省");

		list.add(city1);

		City city2 = new City();
		city2.setCity("济南市");
		city2.setCityCode("4510");
		city2.setCityLevel((byte) 2);
		city2.setCityName("商河县");
		city2.setProvince("山东省");

		list.add(city2);

		return list;
	}

	@RequestMapping("/create")
	@ResponseBody
	public Integer create() {
		City city = new City();
		city.setCity("平昌县");
		city.setCityLevel((byte) 3);
		city.setProvince("四川省");
		//引发事务操作检查
		//city.setCityName("巴中市");
		city.setCityCode("2834");
		city.setProvinceCode("0028");
		city.setState("1");
		try {
			this.cityService.addCity(city);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
