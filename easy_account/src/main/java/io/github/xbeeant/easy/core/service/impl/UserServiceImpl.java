package io.github.xbeeant.easy.core.service.impl;

import io.github.xbeeant.easy.core.mapper.UserMapper;
import io.github.xbeeant.easy.core.model.User;
import io.github.xbeeant.easy.core.service.IUserService;
import io.github.xbeeant.easy.config.AbstractSecurityMybatisPageHelperServiceImpl;
import io.github.xbeeant.easy.rest.vo.RegisterVo;
import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.core.IdWorker;
import io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperDao;
import io.github.xbeeant.spring.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 账号
 */
@Service
public class UserServiceImpl extends AbstractSecurityMybatisPageHelperServiceImpl<User, Long> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public boolean checkPassword(User user, String rawPassword, String ip) {
        return B_CRYPT_PASSWORD_ENCODER.matches(rawPassword, user.getPassword());
    }

    @Override
    public LoginUser<User> loadUserByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("账号或密码错误");
        }
        // 获取权限
        LoginUser<User> loginUser = new LoginUser<>(String.valueOf(user.getId()), user.getNickname(), username, user.getPassword(), Collections.emptyList());
        loginUser.setDetails(user);

        return loginUser;
    }

    @Override
    public ApiResponse<String> register(RegisterVo record) {
        ApiResponse<String> result = new ApiResponse<>();
        // 密码一致性校验
        if (!record.getPassword().equals(record.getConfirm())) {
            result.setResult(1, "两次密码不一致");
            return result;
        }

        // 手机号唯一性验证
        User example = new User();
        example.setMobile(record.getMobile());
        ApiResponse<User> existChkRst = selectOneByExample(example);
        if (existChkRst.getSuccess()) {
            result.setResult(2, "手机号已被注册");
            return result;
        }

        // 邮箱唯一性验证
        example = new User();
        example.setEmail(record.getMail());
        existChkRst = selectOneByExample(example);
        if (existChkRst.getSuccess()) {
            result.setResult(2, "邮箱已被注册");
            return result;
        }
        example = new User();
        example.setAccount(record.getMobile());
        example.setEmail(record.getMail());
        example.setMobile(record.getMobile());
        example.setPrefix(record.getPrefix());
        example.setPassword(record.getPassword());
        ApiResponse<User> userApiResponse = insertSelective(example);
        // result
        result.setResult(userApiResponse.getCode(), userApiResponse.getMsg());
        return result;
    }

    @Override
    public IMybatisPageHelperDao<User, Long> getRepositoryDao() {
        return this.userMapper;
    }

    @Override
    public void setDefaults(User record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }

        if (null != record.getPassword()) {
            record.setPassword(B_CRYPT_PASSWORD_ENCODER.encode(record.getPassword()));
        }
    }
}

