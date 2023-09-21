package com.cfexlib.datasource.config.jpa;

import com.cfexlib.datasource.config.BaseDataSourceConfig;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * refers: <a href="https://github.com/kse-music/mcn-boot-project/blob/master/mcn-boot-autoconfigure/src/main/java/cn/hiboot/mcn/autoconfigure/jpa/JpaConfiguration.java">...</a>
 * refers: <a href="https://github.com/linyongfu2013/springboot-multi-datasource/blob/master/src/main/java/com/imooc/config/jpa/JpaEntityManager.java">...</a>
 */
@Configuration
@EnableTransactionManagement
@Import(BaseDataSourceConfig.class)
@EnableConfigurationProperties(JpaProperties.class)
class JpaDataSourceConfig implements BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Resource(name = "dynamicDataSource")
    private DataSource dynamicDataSource;

    @Resource
    private JpaProperties jpaProperties;

    /**
     * refer: org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration
     * @return JpaVendorAdapter
     */
    @Bean
    @ConditionalOnMissingBean
    public JpaVendorAdapter jpaVendorAdapter() {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(this.jpaProperties.isShowSql());
        if (this.jpaProperties.getDatabase() != null) {
            adapter.setDatabase(this.jpaProperties.getDatabase());
        }
        if (this.jpaProperties.getDatabasePlatform() != null) {
            adapter.setDatabasePlatform(this.jpaProperties.getDatabasePlatform());
        }
        adapter.setGenerateDdl(this.jpaProperties.isGenerateDdl());
        return adapter;
    }

    @Bean
    @ConditionalOnMissingBean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(JpaVendorAdapter jpaVendorAdapter,
                                                                   ObjectProvider<PersistenceUnitManager> persistenceUnitManager,
                                                                   ObjectProvider<EntityManagerFactoryBuilderCustomizer> customizers) {
        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(jpaVendorAdapter,
                this.jpaProperties.getProperties(), persistenceUnitManager.getIfAvailable());
        customizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
        return builder;
    }


    @Bean
    @Primary
    @ConditionalOnMissingBean({ LocalContainerEntityManagerFactoryBean.class, EntityManagerFactory.class })
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        Map<String, String> properties = this.jpaProperties.getProperties();
        properties.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, SpringImplicitNamingStrategy.class.getName());
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, CamelCaseToUnderscoresNamingStrategy.class.getName());
        return builder.dataSource(this.dynamicDataSource)
                .packages(this.getPackagesToScan())
                .properties(properties)
                .mappingResources(this.getMappingResources())
                .jta(false) // not create Jta bean
                .persistenceUnit("jpaPersistenceUnit")
                .build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return this.entityManagerFactoryBean(builder).getObject();
    }

    @Bean
    @ConditionalOnMissingBean(TransactionManager.class)
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    protected String[] getPackagesToScan() {
        List<String> packages = EntityScanPackages.get(this.beanFactory).getPackageNames();
        if (packages.isEmpty() && AutoConfigurationPackages.has(this.beanFactory)) {
            packages = AutoConfigurationPackages.get(this.beanFactory);
        }
        return StringUtils.toStringArray(packages);
    }

    private String[] getMappingResources() {
        List<String> mappingResources = this.jpaProperties.getMappingResources();
        return (!ObjectUtils.isEmpty(mappingResources) ? StringUtils.toStringArray(mappingResources) : null);
    }
}
