package io.github.xbeeant.easy.core.po;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2021/01/09
 */
public class AmapDistrictPo {
    /**
     * count
     */
    @JsonProperty("count")
    private String count;
    /**
     * 行政区列表
     */
    @JsonProperty("districts")
    private List<GeographicPo> districts;
    /**
     * 返回状态说明
     */
    @JsonProperty("info")
    private String info;
    /**
     * 状态码,返回状态说明，10000代表正确，详情参阅info状态表
     */
    @JsonProperty("infocode")
    private String infocode;

    /**
     * 返回结果状态值
     */
    @JsonProperty("status")
    private String status;

    /**
     * 获取 count.
     *
     * @return count 值
     */
    public String getCount() {
        return count;
    }

    /**
     * 设置 count.
     *
     * <p>通过 getCount() 获取 count</p>
     *
     * @param count count
     */
    public void setCount(String count) {
        this.count = count;
    }

    /**
     * 获取 districts.
     *
     * @return districts 值
     */
    public List<GeographicPo> getDistricts() {
        return districts;
    }

    /**
     * 设置 districts.
     *
     * <p>通过 getDistricts() 获取 districts</p>
     *
     * @param districts districts
     */
    public void setDistricts(List<GeographicPo> districts) {
        this.districts = districts;
    }

    /**
     * 获取 info.
     *
     * @return info 值
     */
    public String getInfo() {
        return info;
    }

    /**
     * 设置 info.
     *
     * <p>通过 getInfo() 获取 info</p>
     *
     * @param info info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 获取 infocode.
     *
     * @return infocode 值
     */
    public String getInfocode() {
        return infocode;
    }

    /**
     * 设置 infocode.
     *
     * <p>通过 getInfocode() 获取 infocode</p>
     *
     * @param infocode infocode
     */
    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    /**
     * 获取 status.
     *
     * @return status 值
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置 status.
     *
     * <p>通过 getStatus() 获取 status</p>
     *
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
