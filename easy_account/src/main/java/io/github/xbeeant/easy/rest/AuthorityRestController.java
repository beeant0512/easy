package io.github.xbeeant.easy.rest;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.core.RandomUtil;
import io.github.xbeeant.easy.core.model.User;
import io.github.xbeeant.easy.core.service.IUserService;
import io.github.xbeeant.easy.rest.vo.CurrentUserVo;
import io.github.xbeeant.easy.rest.vo.GeographicVo;
import io.github.xbeeant.easy.rest.vo.RegisterVo;
import io.github.xbeeant.easy.rest.vo.SelectOption;
import io.github.xbeeant.easy.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        // todo send captcha
        String captcha = RandomUtil.mix(6);
        logger.warn("{} {}", phone, captcha);

//        CacheHelper.Remote.set(KeyPrefix.CAPTCHA + phone, captcha, 60L);
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
        User user = SecurityUtil.getCurrentUser();

        ApiResponse<User> userResponse = userService.selectByPrimaryKey(user.getId());
        user = userResponse.getData();
        ApiResponse<CurrentUserVo> result = new ApiResponse<>();
        CurrentUserVo current = new CurrentUserVo();
        current.setName(user.getNickname());
        current.setPhone(user.getPrefix() + "-" + user.getMobile());
        current.setEmail(user.getEmail());
        current.setCountry(user.getCountry());
        GeographicVo geographic = new GeographicVo();
        geographic.setCity(new SelectOption(user.getCity()));
        geographic.setProvince(new SelectOption(user.getProvince()));
        geographic.setDistrict(new SelectOption(user.getDistrict()));
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
     * @see ApiResponse
     * @see String
     * @throws NoSuchAlgorithmException 没有这样的算法
     */
    @ApiOperation(value = "获取加密密钥", notes = "")
    @GetMapping("encrypt")
    public ApiResponse<String> encryptKey(HttpServletRequest request) throws NoSuchAlgorithmException {
        return SecurityUtil.getPublicKey(request);
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
        apiResponse.setData(null != SecurityUtil.getCurrentUser());
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
        User user = SecurityUtil.getCurrentUser();

        ApiResponse<String> result = new ApiResponse<>();
        CurrentUserVo current = new CurrentUserVo();
        current.setName(user.getNickname());
        current.setPhone(user.getPrefix() + "-" + user.getMobile());
        current.setEmail(user.getEmail());
        current.setUserid(String.valueOf(user.getId()));

        return result;
    }
}