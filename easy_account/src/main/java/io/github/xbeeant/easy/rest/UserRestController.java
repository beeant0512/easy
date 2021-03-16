package io.github.xbeeant.easy.rest;


import com.github.pagehelper.Page;
import io.github.xbeeant.core.service.IAbstractService;
import io.github.xbeeant.easy.core.model.User;
import io.github.xbeeant.easy.core.service.IUserService;
import io.github.xbeeant.spring.mybatis.antdesign.PageResponse;
import io.github.xbeeant.spring.mybatis.pagehelper.PageBounds;
import io.github.xbeeant.spring.mybatis.rest.AbstractPagehelperRestController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2021/01/20
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/api/user")
public class UserRestController implements AbstractPagehelperRestController<User, Long> {
    @Autowired
    private IUserService userService;

    @Override
    public IAbstractService<User, Long, PageBounds, PageResponse<User>, Page<User>> getService() {
        return userService;
    }
}
