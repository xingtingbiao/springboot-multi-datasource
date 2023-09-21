package com.cfexlib.datasource.config;

import com.cfexlib.datasource.DynamicDataSource;
import com.cfexlib.datasource.tool.DataSourceManager;
import com.cfexlib.datasource.tool.DataSourceTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration/*(proxyBeanMethods = false)*/
@Import({Properties.HikariProperties.class, DataSourceManager.class})
@EnableConfigurationProperties({DataSourceProperties.class, JdbcProperties.class})
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
        SqlInitializationAutoConfiguration.class })
public class BaseDataSourceConfig {

    @Value("${spring.sql.init.schema-locations:#{null}}")
    private String schemaSql;

    @Value("${spring.sql.init.data-locations:#{null}}")
    private String dataSql;

    @Value("${spring.datasource.type:com.zaxxer.hikari.HikariDataSource}")
    private String type;

    @javax.annotation.Resource
    private DataSourceProperties dataSourceProperties;

    @javax.annotation.Resource
    private List<Properties> properties;


    /**
     * create a default datasource from dataSourceProperties
     * @return DataSource
     */
//    @Bean("defaultDataSource")
    private DataSource defaultDataSource() {
        DataSourceBuilder<?> builder = dataSourceProperties.initializeDataSourceBuilder();
        return DataSourceTool.buildInstance(this.type, builder, properties, null);
    }

//    @Primary
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        DataSource defaultDataSource = this.defaultDataSource();
        dataSourceMap.put("default", defaultDataSource);
        // set default data source
        dynamicDataSource.setDefaultDataSource(defaultDataSource);
        // set dynamic data source
        dynamicDataSource.setDataSources(dataSourceMap);
        log.info("create dynamicDataSource bean...");
        return dynamicDataSource;
    }


    /**
     * @param dataSource is @Primary of dynamicDataSource, can remove @Qualifier("dynamicDataSource") final
     * @return DataSourceInitializer
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer(@Qualifier("dynamicDataSource") final DataSource dataSource) throws IOException {
        if (ObjectUtils.isEmpty(schemaSql) || ObjectUtils.isEmpty(dataSql)) return null;
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//        resourceDatabasePopulator.addScripts(new ClassPathResource(schemaSql), new ClassPathResource(dataSql));
        resourceDatabasePopulator.addScripts(new PathMatchingResourcePatternResolver().getResources(schemaSql));
        resourceDatabasePopulator.addScripts(new PathMatchingResourcePatternResolver().getResources(dataSql));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean
    @Primary
    JdbcTemplate jdbcTemplate(@Qualifier("dynamicDataSource") final DataSource dataSource, JdbcProperties properties) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcProperties.Template template = properties.getTemplate();
        jdbcTemplate.setFetchSize(template.getFetchSize());
        jdbcTemplate.setMaxRows(template.getMaxRows());
        if (template.getQueryTimeout() != null) {
            jdbcTemplate.setQueryTimeout((int) template.getQueryTimeout().getSeconds());
        }
        log.info("create jdbcTemplate bean...");
        return jdbcTemplate;
    }
}
