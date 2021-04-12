package io.github.xbeeant.easy.config.web;

import io.github.xbeeant.core.JsonHelper;
import io.github.xbeeant.easy.util.SecurityHelper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author xiaobiao
 * @version 2021/4/12
 */
@Configuration
public class DecryptOncePerRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ModifyParametersWrapper mParametersWrapper = new ModifyParametersWrapper(request);
        filterChain.doFilter(mParametersWrapper, response);
    }


    /**
     * 继承HttpServletRequestWrapper，创建装饰类，以达到修改HttpServletRequest参数的目的
     */
    private static class ModifyParametersWrapper extends HttpServletRequestWrapper {
        /**
         * 解密后的参数集合
         */
        private Map<String, String[]> decryptParams = new HashMap<>();
        /**
         * 所有参数的Map集合
         */
        private Map<String, String[]> parameterMap;

        public ModifyParametersWrapper(HttpServletRequest request) {
            super(request);
            this.parameterMap = request.getParameterMap();
            if (null != this.parameterMap) {
                String keyid = SecurityHelper.getKeyId((HttpServletRequest) this.getRequest());
                Set<Map.Entry<String, String[]>> parameterEntries = this.parameterMap.entrySet();
                for (Map.Entry<String, String[]> param : parameterEntries) {
                    if ("s".equals(param.getKey())) {
                        decryptParams = new HashMap<>();
                        String decrypt = SecurityHelper.decrypt(keyid, param.getValue()[0]);
                        Map<String, String> map = JsonHelper.toObject(decrypt, Map.class);
                        Set<Map.Entry<String, String>> entries = map.entrySet();
                        for (Map.Entry<String, String> entry : entries) {
                            decryptParams.put(entry.getKey(), new String[]{entry.getValue()});
                        }
                    }
                }
            }
        }

        /**
         * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值
         *
         * @param name 指定参数名
         * @return 指定参数名的值
         */
        @Override
        public String getParameter(String name) {
            String[] results = parameterMap.get(name);
            if (results == null) {
                results = decryptParams.get(name);
            }

            if (null == results) {
                return null;
            }

            return results[0];
        }

        /**
         * 获取所有参数名
         *
         * @return 返回所有参数名
         */
        @Override
        public Enumeration<String> getParameterNames() {
            Vector<String> vector = new Vector<>(parameterMap.keySet());
            vector.addAll(decryptParams.keySet());
            return vector.elements();
        }

        /**
         * 获取指定参数名的值，包括从解密后的数据中获取
         */
        @Override
        public String[] getParameterValues(String name) {
            String[] results = parameterMap.get(name);
            if (results == null) {
                results = decryptParams.get(name);
            }
            return results;
        }
    }
}
