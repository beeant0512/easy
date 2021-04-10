package io.github.xbeeant.easy.core.service.impl;

import io.github.xbeeant.core.IdWorker;
import io.github.xbeeant.easy.config.AbstractSecurityMybatisPageHelperServiceImpl;
import io.github.xbeeant.easy.core.mapper.SecurityKeyMapper;
import io.github.xbeeant.easy.core.model.SecurityKey;
import io.github.xbeeant.easy.core.service.ISecurityKeyService;
import io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 密钥管理
 */
@Service
public class SecurityKeyServiceImpl extends AbstractSecurityMybatisPageHelperServiceImpl<SecurityKey, Long> implements ISecurityKeyService {

    @Autowired
    private SecurityKeyMapper securityKeyMapper;

    @Override
    public IMybatisPageHelperDao<SecurityKey, Long> getRepositoryDao() {
        return this.securityKeyMapper;
    }

    @Override
    public boolean isWithBlobs() {
        return true;
    }

    @Override
    public void setDefaults(SecurityKey record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }
    }
}
