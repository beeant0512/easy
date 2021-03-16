package io.github.xbeeant.easy.core.po;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeographicPo {
    /**
     * 区域编码,街道没有独有的adcode，均继承父类（区县）的adcode
     */
    @JsonProperty("adcode")
    private String adcode;
    /**
     * 区域中心点
     */
    @JsonProperty("center")
    private String center;

    @JsonProperty("citycode")
    private String citycode;
    /**
     * 下级行政区列表，包含district元素
     */
    @JsonProperty("districts")
    private List<GeographicPo> districts;
    /**
     * 行政区划级别
     */
    @JsonProperty("level")
    private String level;
    /**
     * 行政区名称
     */
    @JsonProperty("name")
    private String name;

    public String getAdcode() {
        return adcode;
    }

    public String getCenter() {
        return center;
    }

    public String getCitycode() {
        return citycode;
    }

    public List<GeographicPo> getDistricts() {
        return districts;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public void setDistricts(List<GeographicPo> districts) {
        this.districts = districts;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }
}
