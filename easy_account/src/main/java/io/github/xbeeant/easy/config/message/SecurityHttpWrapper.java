package io.github.xbeeant.easy.config.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaobiao
 * @version 2021/4/5
 */
public class SecurityHttpWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> headers = new HashMap<>(8);

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public SecurityHttpWrapper(HttpServletRequest request, String content) {
        super(request);
    }

    public void putHeader(String key, String value) {
        headers.put(key, value);
    }
}
