package io.github.xbeeant.easy.util;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.core.IdWorker;
import io.github.xbeeant.core.JsonHelper;
import io.github.xbeeant.crypto.KeyString;
import io.github.xbeeant.crypto.asymmetric.RSAUtil;
import io.github.xbeeant.easy.core.model.SecurityKey;
import io.github.xbeeant.easy.core.model.User;
import io.github.xbeeant.easy.core.service.ISecurityKeyService;
import io.github.xbeeant.easy.exception.DecryptException;
import io.github.xbeeant.easy.rest.vo.SecurityKeyVo;
import io.github.xbeeant.spring.security.LoginUser;
import io.github.xbeeant.spring.security.UserHelper;
import io.github.xbeeant.spring.web.SpringContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/11
 */
public class SecurityHelper {
    private static final Logger logger = LoggerFactory.getLogger(SecurityHelper.class);

    public static final String key = "keyid";

    private static ISecurityKeyService securityKeyService;

    private SecurityHelper() {

    }

    public static String getKeyId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String keyId = null;
        for (Cookie cookie : cookies) {
            if (SecurityHelper.key.equals(cookie.getName())) {
                keyId = cookie.getValue();
            }
        }
        return keyId;
    }

    private static ISecurityKeyService getSecurityKeyService() {
        if (null == securityKeyService) {
            securityKeyService = SpringContextProvider.getBean(ISecurityKeyService.class);
        }
        return securityKeyService;
    }

    /**
     * 解密
     *
     * @param keyid        IP地址
     * @param encrypted 解密
     * @return {@link String}
     */
    public static String decrypt(String keyid, String encrypted) {
        ApiResponse<SecurityKey> dbValue = getSecurityKeyService().selectByPrimaryKey(Long.valueOf(keyid));
        String keyPair = dbValue.getData().getKeyPair();
        String privateKey = JsonHelper.toObject(keyPair, KeyString.class).getPrivateKey();
        try {
            byte[] decrypt = RSAUtil.decrypt(privateKey, encrypted);
            return new String(decrypt);
        } catch (Exception e) {
            throw new DecryptException("解密异常", e);
        }
    }

    public static User getCurrentUser() {
        Authentication currentUser = UserHelper.getCurrentUser();
        if (currentUser instanceof AnonymousAuthenticationToken) {
            return null;
        }
        LoginUser<User> principal = (LoginUser<User>) currentUser.getPrincipal();
        return principal.getDetails();
    }

    /**
     * 获取公钥
     *
     * @param request 请求
     * @return {@link ApiResponse<String>}* @throws NoSuchAlgorithmException 没有这样的算法异常
     */
    public static ApiResponse<SecurityKeyVo> getPublicKey(HttpServletRequest request) throws NoSuchAlgorithmException {
        ApiResponse<SecurityKeyVo> response = new ApiResponse<>();
        SecurityKeyVo securityKeyVo = new SecurityKeyVo();
        Cookie[] cookies = request.getCookies();
        String keyId = null;
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (SecurityHelper.key.equals(cookie.getName())) {
                    keyId = cookie.getValue();
                }
            }
        }

        // load from database
        if (null != keyId) {
            ApiResponse<SecurityKey> cachedValue = getSecurityKeyService().selectByPrimaryKey(Long.valueOf(keyId));
            // if database has the key return it
            if (cachedValue.getSuccess()) {
                securityKeyVo.setId(Long.valueOf(keyId));
                KeyString keyString = JsonHelper.toObject(cachedValue.getData().getKeyPair(), KeyString.class);
                securityKeyVo.setKey(keyString.getPublicKey());
                response.setData(securityKeyVo);
                return response;
            }
        }

        // generate a new pair of rsa key and save it
        KeyPair keyPair = RSAUtil.buildKeyPair(2048);
        KeyString keyString = RSAUtil.toKeyString(keyPair);
        securityKeyVo.setKey(keyString.getPublicKey());
        long id = IdWorker.getId();
        securityKeyVo.setId(id);

        response.setData(securityKeyVo);

        // save to database
        SecurityKey securityKey = new SecurityKey();
        securityKey.setId(id);
        securityKey.setKeyPair(JsonHelper.toJsonString(RSAUtil.toKeyString(keyPair)));
        securityKey.setExpireAt(new Date());
        getSecurityKeyService().insertSelective(securityKey);

        return response;
    }
}
