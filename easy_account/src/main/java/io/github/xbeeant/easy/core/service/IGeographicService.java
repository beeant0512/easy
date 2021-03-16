package io.github.xbeeant.easy.core.service;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.easy.core.model.Geographic;
import io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperService;

/**
 * @author mybatis code generator
 * @version Sun Jan 10 19:12:39 CST 2021
 */
public interface IGeographicService extends IMybatisPageHelperService<Geographic, Long> {
    /**
     * 重新同步
     *
     * @param json 高德district的JSON
     * @return {@link ApiResponse<String>}
     */
    ApiResponse<String> resync(String json);
}