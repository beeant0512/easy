package io.github.xbeeant.easy.rest;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.core.RandomHelper;
import io.github.xbeeant.easy.core.model.Cache;
import io.github.xbeeant.easy.core.model.User;
import io.github.xbeeant.easy.core.service.ICacheService;
import io.github.xbeeant.easy.core.service.IUserService;
import io.github.xbeeant.easy.rest.vo.*;
import io.github.xbeeant.easy.util.SecurityHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 认证状态
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2021/01/09
 */
@RestController
@RequestMapping("/api/authroity")
@Api(tags = "认证")
public class AuthorityRestController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityRestController.class);
    private ICacheService cacheService;
    private IUserService userService;

    /**
     * 发送验证码
     *
     * @param phone 电话 {@link String}
     * @return {@link ApiResponse}
     * @see ApiResponse
     * @see Boolean
     */
    @ApiOperation(value = "发送验证码", notes = "")
    @PostMapping("captcha")
    public ApiResponse<Boolean> captcha(String phone) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();

        String captcha = RandomHelper.mix(6);
        logger.warn("{} {}", phone, captcha);

        Cache cache = new Cache();
        cache.setOwner(phone);
        cache.setType("captcha");
        cache.setValue(captcha);
        cacheService.insertSelective(cache);
        return apiResponse;
    }

    /**
     * 验证码状态
     *
     * @return {@link ApiResponse}
     * @see ApiResponse
     * @see Integer
     */
    @ApiOperation(value = "验证码状态", notes = "")
    @GetMapping("captcha")
    public ApiResponse<Integer> captchaStatus() {
        ApiResponse<Integer> apiResponse = new ApiResponse<>();
        // todo send captcha

        return apiResponse;
    }

    /**
     * 当前用户
     *
     * @return {@link ApiResponse<CurrentUserVo>}
     */
    @ApiOperation(value = "当前使用者", notes = "")
    @GetMapping("current")
    public ApiResponse<CurrentUserVo> currentUser() {
        User user = SecurityHelper.getCurrentUser();

        ApiResponse<User> userResponse = userService.selectByPrimaryKey(user.getId());
        user = userResponse.getData();
        ApiResponse<CurrentUserVo> result = new ApiResponse<>();
        CurrentUserVo current = new CurrentUserVo();
        current.setName(user.getNickname());
        current.setPhone(user.getPrefix() + "-" + user.getMobile());
        current.setEmail(user.getEmail());

        GeographicVo geographic = new GeographicVo();
        geographic.setCity(new SelectOption(user.getCityId()));
        geographic.setProvince(new SelectOption(user.getProvinceId()));
        geographic.setDistrict(new SelectOption(user.getDistrictId()));
        current.setGeographic(geographic);
        current.setSignature(user.getProfile());
        current.setUserid(String.valueOf(user.getId()));

        result.setData(current);
        return result;
    }

    /**
     * 获取加密密钥
     *
     * @param request 请求 {@link HttpServletRequest}
     * @return {@link ApiResponse}
     * @throws NoSuchAlgorithmException 没有这样的算法
     * @see ApiResponse
     * @see SecurityKeyVo
     */
    @ApiOperation(value = "获取加密密钥", notes = "")
    @GetMapping("encrypt")
    public ApiResponse<String> encryptKey(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
        ApiResponse<String> result = new ApiResponse<>();
        ApiResponse<SecurityKeyVo> publicKey = SecurityHelper.getPublicKey(request);
        response.addCookie(new Cookie(SecurityHelper.key, String.valueOf(publicKey.getData().getId())));
        result.setData(publicKey.getData().getKey());
        return result;
    }

    /**
     * 登录状态
     *
     * @return {@link ApiResponse}
     * @see ApiResponse
     * @see Boolean
     */
    @ApiOperation(value = "登录状态", notes = "返回当前会话用户登录情况")
    @GetMapping("status")
    public ApiResponse<Boolean> loginStatus() {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setData(null != SecurityHelper.getCurrentUser());
        return apiResponse;
    }

    /**
     * 菜单
     *
     * @return {@link ApiResponse}
     * @see ApiResponse
     * @see List
     */
    @ApiOperation(value = "菜单", notes = "")
    @GetMapping("menus")
    public ApiResponse<List<Object>> menus() {
        ApiResponse<List<Object>> apiResponse = new ApiResponse<>();

        return apiResponse;
    }

    /**
     * 注册
     *
     * @param params RegisterVo {@link RegisterVo}
     * @return {@link ApiResponse}
     * @see ApiResponse
     * @see String
     */
    @ApiOperation(value = "注册", notes = "")
    @PostMapping("register")
    public ApiResponse<String> register(@ApiParam(value = "params", required = true, example = "") @RequestBody RegisterVo params) {
        return userService.register(params);
    }

    @Autowired
    public void setCacheService(ICacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 更新当前用户
     *
     * @return {@link ApiResponse<CurrentUserVo>}
     */
    @ApiOperation(value = "更新当前用户", notes = "")
    @PostMapping("current")
    public ApiResponse<String> updateCurrentUser() {
        User user = SecurityHelper.getCurrentUser();

        ApiResponse<String> result = new ApiResponse<>();
        CurrentUserVo current = new CurrentUserVo();
        current.setName(user.getNickname());
        current.setPhone(user.getPrefix() + "-" + user.getMobile());
        current.setEmail(user.getEmail());
        current.setUserid(String.valueOf(user.getId()));

        return result;
    }
}
