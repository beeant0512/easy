package io.github.xbeeant.easy.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.core.IdWorker;
import io.github.xbeeant.easy.config.AbstractSecurityMybatisPageHelperServiceImpl;
import io.github.xbeeant.easy.core.mapper.GeographicMapper;
import io.github.xbeeant.easy.core.model.Geographic;
import io.github.xbeeant.easy.core.po.AmapDistrictPo;
import io.github.xbeeant.easy.core.po.GeographicPo;
import io.github.xbeeant.easy.core.service.IGeographicService;
import io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperDao;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域
 */
@Service
public class GeographicServiceImpl extends AbstractSecurityMybatisPageHelperServiceImpl<Geographic, Long> implements IGeographicService {
    private static final Logger logger = LoggerFactory.getLogger(GeographicServiceImpl.class);

    private GeographicMapper geographicMapper;

    @Override
    public IMybatisPageHelperDao<Geographic, Long> getRepositoryDao() {
        return this.geographicMapper;
    }

    @Override
    public void setDefaults(Geographic record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }
    }

    @Override
    public ApiResponse<String> resync(String json) {
        ApiResponse<String> result = new ApiResponse<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        AmapDistrictPo amapDistrictPo;
        try {
            amapDistrictPo = mapper.readValue(json, AmapDistrictPo.class);
        } catch (JsonProcessingException e) {
            logger.error("json转对象异常", e);
            result.setResult(500, "json转对象异常");
            return result;
        }
        List<GeographicPo> districts = amapDistrictPo.getDistricts();
        List<Geographic> districtList = new ArrayList<>();
        districtTree2List(districts, districtList, 0L);

        // empty all data
        geographicMapper.truncate();

        // insert new data
        batchInsertSelective(districtList);
        return result;
    }

    private void districtTree2List(List<GeographicPo> districts, List<Geographic> geographics, Long pid) {
        Geographic geographic;
        if (null == districts) {
            return;
        }
        for (GeographicPo districtPo : districts) {
            geographic = new Geographic();
            geographic.setAdcode(districtPo.getAdcode());
            geographic.setCenter(districtPo.getCenter());
            geographic.setCitycode(districtPo.getCitycode());
            geographic.setLevel(districtPo.getLevel());
            geographic.setName(districtPo.getName());
            geographic.setId(IdWorker.getId());
            geographic.setPid(pid);
            geographics.add(geographic);
            if (!CollectionUtils.isEmpty(districtPo.getDistricts())) {
                districtTree2List(districtPo.getDistricts(), geographics, geographic.getId());
            }
        }
    }

    @Autowired
    public void setDistrictMapper(GeographicMapper geographicMapper) {
        this.geographicMapper = geographicMapper;
    }
}