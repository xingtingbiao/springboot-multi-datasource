//package com.cfex.datasource.route;
//
//import java.lang.annotation.*;
//
//@Target(ElementType.ANNOTATION_TYPE)
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//public @interface Custom {
//
//    /**
//     * The name of the parameter in the http request for routing to appoint datasource.
//     * The default is 'marketplaceCode '
//     * @return string
//     */
//    String name() default "marketplaceCode";
//
//    /**
//     * The location of the parameter in the http request for routing to appoint datasource.
//     * default is in path. support in path/query/header
//     * @return Location
//     */
//    Location location() default Location.PATH;
//
//}
