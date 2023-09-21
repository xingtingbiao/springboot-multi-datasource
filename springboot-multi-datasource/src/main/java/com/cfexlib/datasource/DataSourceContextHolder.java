package com.cfexlib.datasource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DataSourceContextHolder {

    public static final String DEFAULT = "default";

    private static final ThreadLocal<String> contextHolder = ThreadLocal.withInitial(() -> DEFAULT);

    public static Set<Object> dataSourceKeys = new HashSet<>();

    public static void setDataSourceKey(String key) {
        contextHolder.set(key);
    }

    public static String getDataSourceKey() {
        return contextHolder.get();
    }

    public static void clearDataSourceKey() {
        contextHolder.remove();
    }

    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }

    public static void addDataSourceKey(Object key) {
        dataSourceKeys.add(key);
    }

    public static void addDataSourceKeys(Collection<?> keys) {
        dataSourceKeys.addAll(keys);
    }

}
