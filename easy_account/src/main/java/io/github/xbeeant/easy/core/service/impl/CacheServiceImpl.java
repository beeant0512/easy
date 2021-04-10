package io.github.xbeeant.easy.core.service.impl;

import io.github.xbeeant.core.IdWorker;
import io.github.xbeeant.easy.config.AbstractSecurityMybatisPageHelperServiceImpl;
import io.github.xbeeant.easy.core.mapper.CacheMapper;
import io.github.xbeeant.easy.core.model.Cache;
import io.github.xbeeant.easy.core.service.ICacheService;
import io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * cache
 */
@Service
public class CacheServiceImpl extends AbstractSecurityMybatisPageHelperServiceImpl<Cache, Long> implements ICacheService {

    @Autowired
    private CacheMapper cacheMapper;

    @Override
    public IMybatisPageHelperDao<Cache, Long> getRepositoryDao() {
        return this.cacheMapper;
    }

    @Override
    public void setDefaults(Cache record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }
    }

    @Override
    public boolean isWithBlobs() {
        return true;
    }
}
