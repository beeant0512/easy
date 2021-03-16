package io.github.xbeeant.easy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import io.github.xbeeant.spring.mybatis.SqlSessionFactoryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author huangxiaobiao
 * @date 2020/11/29
 */
@Configuration
@MapperScan(annotationClass = Mapper.class, basePackages = {"io.github.xbeeant.easy.file.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfiguration {
    /**
     * 将MybatisConfig类中初始化的对象注入进来
     */
    @Resource(name = "mybatisConfigurationCustomizer")
    private ConfigurationCustomizer configurationCustomizer;

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DruidDataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        return SqlSessionFactoryUtil.getSqlSessionFactory(dataSource
                , "classpath:/mybatis/mysql/easy_file/**/**.xml"
                , "io.github.xbeeant.easy.file.model"
                , configurationCustomizer);
    }

    /**
     * SqlSessionTemplate 是 SqlSession接口的实现类，是spring-mybatis中的，实现了SqlSession线程安全
     */
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
