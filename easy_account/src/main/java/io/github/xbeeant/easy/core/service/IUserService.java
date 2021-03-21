package io.github.xbeeant.easy.core.service;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.easy.core.model.User;
import io.github.xbeeant.easy.rest.vo.RegisterVo;
import io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperService;
import io.github.xbeeant.spring.security.LoginUser;

/**
 * 用户服务
 */
public interface IUserService extends IMybatisPageHelperService<User, Long> {

    /**
     * 检查密码
     *
     * @param user        用户 {@link User}
     * @param rawPassword 原始密码 {@link String}
     * @param ip          Ip {@link String}
     * @return {@link boolean}
     */
    boolean checkPassword(User user, String rawPassword, String ip);

    /**
     * 按用户名加载用户
     *
     * @param username 用户 {@link String}
     * @return {@link LoginUser}
     * @see LoginUser
     * @see User
     */
    LoginUser<User> loadUserByUsername(String username);

    /**
     * 注册
     *
     * @param record 记录
     * @return {@link ApiResponse}
     * @see ApiResponse
     * @see String
     */
    ApiResponse<String> register(RegisterVo record);
}
