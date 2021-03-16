package io.github.xbeeant.easy.config.security;

import io.github.xbeeant.easy.core.model.User;
import io.github.xbeeant.easy.core.service.IUserService;
import io.github.xbeeant.easy.util.SecurityUtil;
import io.github.xbeeant.spring.security.LoginParamters;
import io.github.xbeeant.spring.security.LoginUser;
import io.github.xbeeant.spring.web.SpringContextProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 数据库账号密码认证提供者
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/03
 */
public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static IUserService springUserService;

    @Override
    @SuppressWarnings("unchecked")
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
        // 密码验证
        LoginUser<User> details = (LoginUser<User>) userDetails;
        User user = details.getDetails();
        // 密码解密
        LoginParamters loginParams = (LoginParamters) authentication.getDetails();
        String cryptPassword = authentication.getCredentials().toString();
        String decryptPassword = "";
        if (cryptPassword != null) {
            decryptPassword = SecurityUtil.decrypt(loginParams.getIp(), cryptPassword);
        }
        // 密码校验
        boolean matches = getSpringUserService().checkPassword(user, decryptPassword, loginParams.getIp());

        if (!matches) {
            throw new BadCredentialsException("账号密码错误");
        }

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {

        try {
            LoginUser<User> loadedUser = getSpringUserService().loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            } else {
                return loadedUser;
            }
        } catch (UsernameNotFoundException | InternalAuthenticationServiceException usernameNotFoundException) {
            throw usernameNotFoundException;
        } catch (Exception var6) {
            throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
        }
    }

    /**
     * Getter for property 'springUserService'.
     *
     * @return Value for property 'springUserService'.
     */
    public static IUserService getSpringUserService() {
        if (null == springUserService) {
            springUserService = SpringContextProvider.getBean(IUserService.class);
        }
        return springUserService;
    }
}
