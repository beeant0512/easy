package io.github.xbeeant.easy.config.security;

import io.github.xbeeant.spring.security.filter.MoreParameterAuthenticationFilter;
import io.github.xbeeant.spring.security.handler.AuthenticationFailedHandler;
import io.github.xbeeant.spring.security.handler.LogoutSuccessedHandler;
import io.github.xbeeant.spring.security.handler.RestAccessDeniedHandler;
import io.github.xbeeant.spring.security.handler.UnauthorizedAuthenticationEntryPoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置
 *
 * @author huangxiaobiao
 * @date 2020/11/29
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    /**
     * 已登录，未授权异常处理`
     *
     * @return {@link AccessDeniedHandler}
     */
    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    /**
     * 管理后台权限配置
     *
     * @author xiaobiao
     * @version 1.0.0
     * @date 2020/12/07
     */
    @Configuration
    @ConfigurationProperties(prefix = "app")
    public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private String[] permit;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();
            String loginApi = "/api/login";
            // 所有的人都可以访问的路径
            http.authorizeRequests()
                    .antMatchers(permit).permitAll()
                    .antMatchers("/api/**").fullyAuthenticated();

            /*
             * 开启自动配置的登录功能，如果没有登录就会来到登录页面
             * 1. 会自动生成登录页面 /login
             * 2. 登录失败会自动重定向到 /login?error
             */
            http.formLogin()
                    .loginPage("/")
                    .loginProcessingUrl(loginApi);
            /*
             * 开启自动配置的退出功能：
             * 1. 访问/logout请求，用户注销，清楚session
             * 2. 注销成功后重定向到 login?logout，，可以通过logoutSuccessUrl("/")自定义
             */
            http.logout()
                    .logoutUrl("/api/logout")
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler(new LogoutSuccessedHandler())
                    .deleteCookies("SESSION");

            // 未登录异常处理
            http.exceptionHandling().authenticationEntryPoint(new UnauthorizedAuthenticationEntryPoint());
            // 自定义登录求参数获取
            MoreParameterAuthenticationFilter authenticationFilter = new MoreParameterAuthenticationFilter(authenticationManagerBean(), loginApi);
            MoreParameterAuthenticationFilter.setUsername("userName");
            // 自定义失败处理器
            authenticationFilter.setAuthenticationFailureHandler(new AuthenticationFailedHandler());
            // 自定义成功处理器
            authenticationFilter.setAuthenticationSuccessHandler(new LoginSuccessedHandler());
            http.addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() {
            return new DaoAuthenticationProvider();
        }

        public void setPermit(String[] permit) {
            this.permit= permit;
        }
    }
}
