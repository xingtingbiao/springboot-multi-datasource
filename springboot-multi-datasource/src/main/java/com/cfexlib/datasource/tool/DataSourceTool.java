package com.cfexlib.datasource.tool;

import com.cfexlib.datasource.config.Properties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;


@Slf4j
public class DataSourceTool {

    public static DataSource buildInstance(String type, DataSourceBuilder<?> builder, List<Properties> properties, String poolName) {
        return switch (type) {
            case "com.zaxxer.hikari.HikariDataSource" -> hikariDataSource(builder, properties, poolName);
            //todo durid
            default -> throw new  RuntimeException("not support datasource type of " + type);
        };
    }

    private static HikariDataSource hikariDataSource(DataSourceBuilder<?> builder, List<Properties> properties, String poolName) {
        Properties.HikariProperties hikariProperties = null;
        for (Properties p : properties) {
            if (p instanceof Properties.HikariProperties) {
                hikariProperties = (Properties.HikariProperties) p;
                break;
            }
        }
        if (null == hikariProperties) {
            throw new  RuntimeException("No HikariProperties bean found.");
        }
        HikariDataSource dataSource = builder.type(HikariDataSource.class).build();
        dataSource.setPoolName(StringUtils.hasText(poolName) ? poolName : hikariProperties.getPoolName());
        if (null != hikariProperties.getMaximumPoolSize()) {
            dataSource.setMaximumPoolSize(hikariProperties.getMaximumPoolSize());
        }
        if (null != hikariProperties.getMinimumIdle()) {
            dataSource.setMinimumIdle(hikariProperties.getMinimumIdle());
        }
        return dataSource;
    }

}
