package com.cfexlib.datasource.config.jdbc;

import com.cfexlib.datasource.route.DynamicDataSourceAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * refer: <a href="https://www.jb51.net/article/248042.htm">...</a>
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({JdbcDataSourceConfig.class, DynamicDataSourceAspect.class})
public @interface EnableJdbcMultiDataSource {
}
