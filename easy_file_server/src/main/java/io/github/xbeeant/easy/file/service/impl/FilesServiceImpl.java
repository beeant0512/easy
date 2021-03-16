package io.github.xbeeant.easy.file.service.impl;

import io.github.xbeeant.core.IdWorker;
import io.github.xbeeant.easy.config.AbstractSecurityMybatisPageHelperServiceImpl;
import io.github.xbeeant.easy.file.mapper.FilesMapper;
import io.github.xbeeant.easy.file.model.File;
import io.github.xbeeant.easy.file.service.IFilesService;
import io.github.xbeeant.spring.mybatis.pagehelper.AbstractMybatisPageHelperServiceImpl;
import io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * easy_files
 */
@Service
public class FilesServiceImpl extends AbstractSecurityMybatisPageHelperServiceImpl<File, Long> implements IFilesService {
    @Autowired
    private FilesMapper easyFilesMapper;

    @Override
    public IMybatisPageHelperDao<File, Long> getRepositoryDao() {
        return this.easyFilesMapper;
    }

    @Override
    public void setDefaults(File record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }
    }
}