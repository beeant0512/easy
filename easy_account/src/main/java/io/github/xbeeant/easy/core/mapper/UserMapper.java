package io.github.xbeeant.easy.core.mapper;

import io.github.xbeeant.easy.core.model.User;
import io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mybatis code generator
 * @version Sat Jan 09 21:48:13 CST 2021
 */
@Mapper
public interface UserMapper extends IMybatisPageHelperDao<User, Long> {

    /**
     * 获取用户
     *
     * @param username 用户名
     * @return {@link User}
     */
    User selectByUsername(String username);
}
