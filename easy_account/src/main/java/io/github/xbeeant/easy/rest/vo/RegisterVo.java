package io.github.xbeeant.easy.rest.vo;

/**
 * 注册信息
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/10
 */
public class RegisterVo {
    /**
     * 验证码
     */
    private String captcha;

    /**
     * 密码确认
     */
    private String confirm;

    /**
     * 邮件
     */
    private String mail;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号前缀（地区）
     */
    private String prefix;


    /**
     * 获取验证码
     *
     * @return captcha 验证码
     */
    public String getCaptcha() {
        return this.captcha;
    }

    /**
     * 获取密码确认
     *
     * @return confirm 密码确认
     */
    public String getConfirm() {
        return this.confirm;
    }

    /**
     * 获取邮件
     *
     * @return mail 邮件
     */
    public String getMail() {
        return this.mail;
    }

    /**
     * 获取手机号
     *
     * @return mobile 手机号
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * 获取密码
     *
     * @return password 密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 获取手机号前缀（地区）
     *
     * @return prefix 手机号前缀（地区）
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * 设置验证码
     *
     * @param captcha 验证码
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /**
     * 设置密码确认
     *
     * @param confirm 密码确认
     */
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    /**
     * 设置邮件
     *
     * @param mail 邮件
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 设置手机号前缀（地区）
     *
     * @param prefix 手机号前缀（地区）
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
