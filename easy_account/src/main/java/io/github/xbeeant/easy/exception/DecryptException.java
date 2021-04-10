package io.github.xbeeant.easy.exception;

/**
 * 解密异常
 *
 * @author xiaobiao
 * @version 2021/4/6
 */
public class DecryptException extends RuntimeException {

    public DecryptException(String message, Exception e) {
        super(message, e);
    }
}
