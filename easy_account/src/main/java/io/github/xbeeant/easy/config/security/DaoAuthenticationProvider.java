package io.github.xbeeant.easy.config.security;

import com.github.pagehelper.util.StringUtil;
import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.easy.core.model.Cache;
import io.github.xbeeant.easy.core.model.User;
import io.github.xbeeant.easy.core.service.ICacheService;
import io.github.xbeeant.easy.core.service.IUserService;
import io.github.xbeeant.easy.util.SecurityHelper;
import io.github.xbeeant.spring.security.LoginParamters;
import io.github.xbeeant.spring.security.LoginUser;
import io.github.xbeeant.spring.web.SpringContextProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.thymeleaf.cache.ICache;

/**
 * 数据库账号密码认证提供者
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/03
 */
public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static IUserService springUserService;

    private static ICacheService cacheService;

    @Override
    @SuppressWarnings("unchecked")
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
        // 密码验证
        LoginUser<User> details = (LoginUser<User>) userDetails;
        User user = details.getDetails();
        // 密码解密
        LoginParamters loginParams = (LoginParamters) authentication.getDetails();

        // 密码校验
        boolean valid = false;
        String captcha = String.valueOf(loginParams.getExtras().get("captcha"));
        if (StringUtil.isNotEmpty(captcha)) {
            String cacheId = (String) loginParams.getExtras().get("cache_id");
            ApiResponse<Cache> cacheResponse = getCacheService().selectByPrimaryKey(Long.valueOf(cacheId));
            if (cacheResponse.getSuccess() && cacheResponse.getData().getValue().equals(captcha)) {
                return;
            }
        } else {
            String cryptPassword = authentication.getCredentials().toString();
            String decryptPassword = "";
            if (cryptPassword != null) {
                decryptPassword = SecurityHelper.decrypt(loginParams.getIp(), cryptPassword);
            }
            // 密码校验
            valid = getSpringUserService().checkPassword(user, decryptPassword, loginParams.getIp());
        }



        if (!valid) {
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

    public static ICacheService getCacheService() {
        if (null == cacheService) {
            cacheService = SpringContextProvider.getBean(ICacheService.class);
        }
        return cacheService;
    }
}
