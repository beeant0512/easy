package io.github.xbeeant.easy.config.message;

import io.github.xbeeant.core.JsonHelper;
import io.github.xbeeant.easy.util.SecurityHelper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xiaobiao
 * @version 2021/4/5
 */
public class SecurityHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String s = webRequest.getParameter("s");
        String keyid = SecurityHelper.getKeyId(((ServletWebRequest) webRequest).getRequest());
        String decrypt = SecurityHelper.decrypt(keyid, s);
        Map<String, Object> map = JsonHelper.toObject(decrypt, Map.class);

        String parameterName = parameter.getParameterName();
        return map.get(parameterName);
    }

}
