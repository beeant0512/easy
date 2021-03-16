package io.github.xbeeant.easy.core.mapper;

import io.github.xbeeant.easy.core.model.Geographic;
import io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mybatis code generator
 * @version Sun Jan 10 19:12:39 CST 2021
 */
@Mapper
public interface GeographicMapper extends IMybatisPageHelperDao<Geographic, Long> {
    /**
     * 截断
     */
    void truncate();
}