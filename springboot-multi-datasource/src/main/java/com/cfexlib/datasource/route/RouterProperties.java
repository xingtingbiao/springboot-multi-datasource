package com.cfexlib.datasource.route;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@Data
@ConfigurationProperties(prefix = "datasource.router")
public class RouterProperties {
    /**
     * It will control whether @DataSourceRouter in all codes takes effect.
     * The default value of true is effective, and false is not
     */
    private boolean enable = true;

    /**
     * jdbc url format string.
     * default is "jdbc:%s://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8"
     * you can override it: datasource.router.url-format=
     */
    private String urlFormat = "jdbc:%s://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8";

    /**
     * driver class name.
     * default is "com.mysql.cj.jdbc.Driver"
     */
    private String driver = "com.mysql.cj.jdbc.Driver";

    /**
     * current environment
     */
    private String env;

    /**
     * customized team name
     */
    private String team;

    /**
     * Customized application name
     */
    private String app;

    public void setUrlFormat(String urlFormat) {
        this.urlFormat = StringUtils.hasText(urlFormat) ? urlFormat : this.urlFormat;
    }

    public void setDriver(String driver) {
        this.driver = StringUtils.hasText(driver) ? driver : "com.mysql.cj.jdbc.Driver";
    }
}
