package io.github.xbeeant.easy.config;

import io.github.xbeeant.core.BaseModelObject;
import io.github.xbeeant.spring.mybatis.pagehelper.AbstractMybatisPageHelperServiceImpl;

/**
 * mybatis page helper for spring security
 *
 * @author huangxiaobiao
 * @date 2020/11/29
 */
public abstract class AbstractSecurityMybatisPageHelperServiceImpl<T extends BaseModelObject<K>, K> extends AbstractMybatisPageHelperServiceImpl<T, K> {
    @Override
    public String getActorId(T record) {
       return "";
    }
}
