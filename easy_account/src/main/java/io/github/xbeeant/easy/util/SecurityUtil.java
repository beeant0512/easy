package io.github.xbeeant.easy.util;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.crypto.KeyString;
import io.github.xbeeant.crypto.asymmetric.RSAUtil;
import io.github.xbeeant.easy.core.model.User;
import io.github.xbeeant.spring.security.LoginUser;
import io.github.xbeeant.spring.security.UserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/11
 */
public class SecurityUtil {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {

    }

    /**
     * 解密
     *
     * @param ip        IP地址
     * @param encrypted 解密
     * @return {@link String}
     */
    public static String decrypt(String ip, String encrypted) {
//        String privateKey = CacheHelper.Remote.get(KeyPrefix.RSA_KEY + ip);
        // todo
        String privateKey = "";
        try {
            byte[] decrypt = RSAUtil.decrypt(privateKey, encrypted);
            return new String(decrypt);
        } catch (Exception e) {
            logger.error("解密失败", e);
            return null;
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
    public static ApiResponse<String> getPublicKey(HttpServletRequest request) throws NoSuchAlgorithmException {
        KeyPair keyPair = RSAUtil.buildKeyPair(2048);
        KeyString keyString = RSAUtil.toKeyString(keyPair);
        ApiResponse<String> response = new ApiResponse<>();
//        CacheHelper.Remote.set(KeyPrefix.RSA_KEY + RequestUtil.getIp(request), keyString.getPrivateKey(), 180L);

        response.setData(keyString.getPublicKey());
        return response;
    }
}
