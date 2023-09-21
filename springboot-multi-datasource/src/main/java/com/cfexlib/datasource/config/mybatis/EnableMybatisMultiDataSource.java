package com.cfexlib.datasource.config.mybatis;

import com.cfexlib.datasource.route.DynamicDataSourceAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * refer: <a href="https://www.jb51.net/article/248042.htm">...</a>
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MybatisDataSourceConfig.class, DynamicDataSourceAspect.class})
public @interface EnableMybatisMultiDataSource {
}
