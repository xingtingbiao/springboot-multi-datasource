package com.cfexlib.datasource.tool;

import com.cfexlib.datasource.DataSourceContextHolder;
import com.cfexlib.datasource.DynamicDataSource;
import com.cfexlib.datasource.aws.SecretManagerTool;
import com.cfexlib.datasource.aws.SecretResult;
import com.cfexlib.datasource.config.Properties;
import com.cfexlib.datasource.route.DataSourceRouter;
import com.cfexlib.datasource.route.RouterProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

import static com.cfexlib.datasource.DataSourceContextHolder.DEFAULT;

@Slf4j
@Data
@Component(value = "dataSourceManager")
public class DataSourceManager {

    @Value("${spring.profiles.active:#{null}}")
    private String springEnv;

    @Value("${spring.datasource.type:com.zaxxer.hikari.HikariDataSource}")
    private String type;

    @Value("${spring.datasource.driver-class-name:com.mysql.cj.jdbc.Driver}")
    private String driverClassName;

    @Resource
    private List<Properties> properties;

    @Resource
    private RouterProperties routerProperties;

    private final DynamicDataSource dynamicDataSource;

    public DataSourceManager(DataSource dynamicDataSource) {
        this.dynamicDataSource = (DynamicDataSource) dynamicDataSource;
    }


    /**
     * get one datasource from dynamicDataSource, if exists get one, if not create a one
     * @param keyValue Tenant identification information, such as marketplaceCode
     */
    public DataSource getOrCreate(String keyValue) {
        String secretId = this.checkOrCreate(null, keyValue);
        return dynamicDataSource.getOneByKey(secretId);
    }

    /**
     * Check whether the tenant's datasource exists, routing it if it exists, and add it if it does not
     * @param keyValue Tenant identification information, such as marketplaceCode
     */
    public void routing(String keyValue) {
        this.routing(null, keyValue);
    }

    /**
     * routing to default datasource
     */
    public void routingToDefault() {
        DataSourceContextHolder.setDataSourceKey(DEFAULT);
    }

    public void routing(DataSourceRouter router, String keyValue) {
        String secretId = this.checkOrCreate(router, keyValue);
        log.trace("routing datasource to tenant: {}, secretId: {}", keyValue, secretId);
        DataSourceContextHolder.setDataSourceKey(secretId);
    }

    private String checkOrCreate(DataSourceRouter router, String keyValue) {
        String secretId = this.getSecretId(router, keyValue);
        // todo not judge whether the secret was updated
        if (!DataSourceContextHolder.containDataSourceKey(secretId)) {
            log.info("creating datasource to tenant: {}, secretId: {}", keyValue, secretId);
            SecretResult secret = SecretManagerTool.getClassSecret(secretId);
            String jdbcUrlFormat = (null != router && StringUtils.hasText(router.urlFormat())) ?
                    router.urlFormat() : this.routerProperties.getUrlFormat();
            String driver = StringUtils.hasText(this.driverClassName) ? this.driverClassName : this.routerProperties.getDriver();
            String dbName = StringUtils.hasText(secret.getDbname()) ? secret.getDbname() : secret.getSchema();
            DataSourceBuilder<?> builder = DataSourceBuilder.create()
                    .driverClassName(driver)
                    .url(String.format(jdbcUrlFormat, secret.getEngine(), secret.getHost(), secret.getPort(), dbName))
                    .username(secret.getUsername())
                    .password(secret.getPassword());
            DataSource dataSource = DataSourceTool.buildInstance(type, builder, properties, "HikariPool-" + keyValue);
            dynamicDataSource.addDataSource(secretId, dataSource);
        }
        return secretId;
    }


    /**
     * get secretId from secret manager
     * @param router DataSourceRouter annotation
     * @param keyValue such as ${marketplaceCode}
     * @return secretId
     */
    private String getSecretId(DataSourceRouter router, String keyValue) {
        String env = StringUtils.hasText(this.routerProperties.getEnv()) ? this.routerProperties.getEnv() : springEnv;
        String teamName;
        String appName;
        if (null == router) {
            teamName = this.routerProperties.getTeam();
            appName = this.routerProperties.getApp();
        } else {
            teamName = StringUtils.hasText(router.team()) ? router.team() : this.routerProperties.getTeam();
            appName = StringUtils.hasText(router.app()) ? router.app() : this.routerProperties.getApp();
        }

        if (ObjectUtils.isEmpty(env)) {
            throw new RuntimeException("build secret-id error: missing spring parameter of datasource.router.env or spring.profiles.active.");
        }
        if (ObjectUtils.isEmpty(teamName)) {
            throw new RuntimeException("build secret-id error: missing spring parameter of datasource.router.team.");
        }
        if (ObjectUtils.isEmpty(appName)) {
            throw new RuntimeException("build secret-id error: missing spring parameter of datasource.router.app.");
        }
        return SecretManagerTool.selfDefineSecretId(env, teamName, appName, keyValue);
    }

}
