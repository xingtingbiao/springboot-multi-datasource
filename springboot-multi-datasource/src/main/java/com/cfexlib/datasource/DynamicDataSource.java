package com.cfexlib.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://cloud.tencent.com/developer/article/2040512">...</a>
 * <a href="https://blog.csdn.net/u014528861/article/details/116655292">...</a>
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> dataSources = new HashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceKey();
    }

    public void setDefaultDataSource(Object defaultDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource);
    }

    public void setDataSources(Map<Object, Object> dataSources) {
        this.dataSources = dataSources;
        super.setTargetDataSources(dataSources);
        DataSourceContextHolder.addDataSourceKeys(dataSources.keySet());
    }

    public void addDataSource(String key, DataSource dataSource) {
        this.dataSources.put(key, dataSource);
        super.setTargetDataSources(this.dataSources);
        DataSourceContextHolder.addDataSourceKey(key);
        // must execute this method，will init targetDataSources to resolvedDataSources，only in this way can dynamic switching work
        super.afterPropertiesSet();
    }

    public void addDataSources(Map<Object, Object> addedDataSources) {
        this.dataSources.putAll(addedDataSources);
        super.setTargetDataSources(this.dataSources);
        DataSourceContextHolder.addDataSourceKeys(addedDataSources.keySet());
        super.afterPropertiesSet();
    }

    public DataSource getOneByKey(String key) {
        return (DataSource) this.dataSources.get(key);
    }
}
