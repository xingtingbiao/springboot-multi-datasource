package com.cfexlib.datasource.route;

import java.lang.annotation.*;

/**
 * refer: <a href="https://www.jb51.net/article/248042.htm">...</a>
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceRouter {
    /**
     * The switch of dynamic routing datasource.
     * true: enabled, false: closed. default true.
     * @return boolean
     */
    boolean value() default true;

    /**
     * The keyName of the parameter in the http request for routing to appoint datasource.
     * default is 'marketplaceCode'
     * @return string
     */
    String keyName() default "marketplaceCode";

    /**
     * The keyValue is a specific value to get tenant info for routing to appoint datasource.
     * default is ''
     * Note: After this attribute is assigned, the keyName/location will be invalidated
     * @return string
     */
    String keyValue() default "";

    /**
     * The location of the parameter in the http request for routing to appoint datasource.
     * default is in path. support in path/query/header
     * @return Location
     */
    Location location() default Location.PATH;

//    Custom custom() default @Custom();

    /**
     * jdbc url format.
     * default is "jdbc:%s://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8"
     * it will override other config
     * @return String
     */
    String urlFormat() default "jdbc:%s://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8";

    /**
     * team name.
     * default is ""
     * @return String
     */
    String team() default "";

    /**
     * application name.
     * default is ""
     * @return String
     */
    String app() default "";

}
