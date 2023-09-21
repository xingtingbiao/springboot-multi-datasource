//package com.cfex.datasource;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import javax.sql.DataSource;
//
//@Slf4j
//@Configuration
//public class DynamicDataSourceInit {
//    // todo @Autowired Tenant mapper to get Tenant info
//
//    private final DynamicDataSource dynamicDataSource;
//
//    public DynamicDataSourceInit(DataSource dynamicDataSource) {
//        this.dynamicDataSource = (DynamicDataSource) dynamicDataSource;
//    }
//
//    @PostConstruct
//    public void initDataSource() {
//        log.info("=====todo init dynamic datasource=====");
////        Map<Object, Object> dataSourceMap = new HashMap<>();
////        List<TenantInfo> tenantList = tenantInfoService.InitTenantInfo();
////        for (TenantInfo tenantInfo : tenantList) {
////            log.info(tenantInfo.toString());
////            HikariDataSource dataSource = new HikariDataSource();
////            dataSource.setDriverClassName(tenantInfo.getDatasourceDriver());
////            dataSource.setJdbcUrl(tenantInfo.getDatasourceUrl());
////            dataSource.setUsername(tenantInfo.getDatasourceUsername());
////            dataSource.setPassword(tenantInfo.getDatasourcePassword());
////            dataSourceMap.put(tenantInfo.getTenantId(), dataSource);
////        }
////
////        dynamicDataSource.addDataSources(dataSourceMap);
////        dynamicDataSource.afterPropertiesSet();
//    }
//}
