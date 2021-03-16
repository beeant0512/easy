package io.github.xbeeant.easy.rest.vo;

public class GeographicVo {
    /**
     * city
     */
    private SelectOption city;
    private SelectOption district;
    private SelectOption province;

    /**
     * 获取 district.
     *
     * @return district 值
     */
    public SelectOption getDistrict() {
        return district;
    }

    /**
     * 设置 district.
     *
     * <p>通过 getDistrict() 获取 district</p>
     *
     * @param district district
     */
    public void setDistrict(SelectOption district) {
        this.district = district;
    }

    public SelectOption getCity() {
        return city;
    }

    public void setCity(SelectOption city) {
        this.city = city;
    }

    public SelectOption getProvince() {
        return province;
    }

    public void setProvince(SelectOption province) {
        this.province = province;
    }
}
