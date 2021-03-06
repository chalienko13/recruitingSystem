package com.netcracker.solutions.kpi.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/app.properties")
@ComponentScan("com.netcracker.solutions.kpi")
@EnableJpaRepositories("com.netcracker.solutions.kpi")
public class DataConfig {

    private static final String DATABASE_DRIVER_PROP = "db.driver";
    private static final String DATABASE_PASSWD_PROP = "db.password";
    private static final String DATABASE_URL_PROP = "db.url";
    private static final String DATABASE_USERNAME_PROP = "db.username";

    private static final String ENTITY_PACKAGES = "com.netcracker.solutions.kpi.persistence.model";

    private static final String HIBERNATE_DIALECT_PROP = "db.hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL_PROP = "db.hibernate.show_sql";
    private static final String HIBERNATE_HBM2DDL_AUTO_PROP = "db.hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_FLUSH_MODE_PROP = "db.org.hibernate.flushMode";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(DATABASE_DRIVER_PROP));
        dataSource.setUrl(env.getRequiredProperty(DATABASE_URL_PROP));
        dataSource.setUsername(env.getRequiredProperty(DATABASE_USERNAME_PROP));
        dataSource.setPassword(env.getRequiredProperty(DATABASE_PASSWD_PROP));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGES);
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    private Properties getHibernateProperties() {
        Properties hiberProps = new Properties();
        hiberProps.put(HIBERNATE_DIALECT_PROP, env.getRequiredProperty(HIBERNATE_DIALECT_PROP));
        hiberProps.put(HIBERNATE_SHOW_SQL_PROP, env.getRequiredProperty(HIBERNATE_SHOW_SQL_PROP));
        hiberProps.put(HIBERNATE_HBM2DDL_AUTO_PROP, env.getProperty(HIBERNATE_HBM2DDL_AUTO_PROP));

        //hiberProps.put(HIBERNATE_FLUSH_MODE_PROP, env.getProperty(HIBERNATE_FLUSH_MODE_PROP));
        return hiberProps;
    }
}
