package io.github.xbeeant.easy.file.mapper;

import io.github.xbeeant.easy.file.model.File;
import io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mybatis code generator
 * @version Sat Jan 30 22:21:52 CST 2021
 */
@Mapper
public interface FilesMapper extends IMybatisPageHelperDao<File, Long> {
}