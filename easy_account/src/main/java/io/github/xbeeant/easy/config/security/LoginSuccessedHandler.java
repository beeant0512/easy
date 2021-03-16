package io.github.xbeeant.easy.config.security;

import io.github.xbeeant.spring.security.handler.AuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/13
 */
public class LoginSuccessedHandler extends AuthenticationSuccessHandler {
    @Override
    public Object setData(Authentication authentication) {
        return null;
    }
}
