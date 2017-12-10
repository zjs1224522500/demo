package me.elvis.demo.support.pojo;

public class City {

	/**
	 * 仅用于数据库中做主键，不赋予任何实质含义
	 */
	private Short cityId;

	private String cityCode;

	private Byte cityLevel;

	private String provinceCode;

	private String state;

	private String cityName;

	private String city;

	private String province;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode == null ? null : cityCode.trim();
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode == null ? null : provinceCode.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName == null ? null : cityName.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public Short getCityId() {
		return cityId;
	}

	public void setCityId(Short cityId) {
		this.cityId = cityId;
	}

	public Byte getCityLevel() {
		return cityLevel;
	}

	public void setCityLevel(Byte cityLevel) {
		this.cityLevel = cityLevel;
	}

	@Override
	public String toString() {
		return "CityCodeCn{" + "cityId=" + cityId + ", cityCode='" + cityCode + '\''
				+ ", cityLevel=" + cityLevel + ", provinceCode='" + provinceCode + '\''
				+ ", state='" + state + '\'' + ", cityName='" + cityName + '\'' + ", city='" + city
				+ '\'' + ", province='" + province + '\'' + '}';
	}
}