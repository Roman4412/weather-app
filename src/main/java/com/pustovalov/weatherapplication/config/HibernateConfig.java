package com.pustovalov.weatherapplication.config;

import com.pustovalov.weatherapplication.entity.Location;
import com.pustovalov.weatherapplication.entity.Session;
import com.pustovalov.weatherapplication.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class HibernateConfig {

    @Value("${jakarta.persistence.jdbc.url}")
    private String url;

    @Value("${jakarta.persistence.jdbc.user}")
    private String user;

    @Value("${jakarta.persistence.jdbc.password}")
    private String password;

    @Value("${jakarta.persistence.jdbc.driver}")
    private String driver;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.show-sql}")
    private String showSql;

    @Value("${hibernate.format-sql}")
    private String formatSql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Bean(value = "sessionFactory", destroyMethod = "close")
    @Profile("dev")
    public SessionFactory sessionFactoryDev() {
        log.info("create sessionFactory for test profile");
        log.info("db url: " + url);
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder().applySetting("jakarta.persistence.jdbc.driver", driver)
                                                    .applySetting("jakarta.persistence.jdbc.url", url)
                                                    .applySetting("jakarta.persistence.jdbc.user", user)
                                                    .applySetting("jakarta.persistence.jdbc.password", password)
                                                    .applySetting("hibernate.dialect", dialect)
                                                    .applySetting("hibernate.show_sql", showSql)
                                                    .applySetting("hibernate.format_sql", formatSql)
                                                    .applySetting("hibernate.hbm2ddl.auto", hbm2ddlAuto)
                                                    .build();

        return new MetadataSources(registry).addAnnotatedClass(User.class)
                                            .addAnnotatedClass(Session.class)
                                            .addAnnotatedClass(Location.class)
                                            .buildMetadata()
                                            .buildSessionFactory();
    }

    @Bean(value = "sessionFactory", destroyMethod = "close")
    @Profile("test")
    public SessionFactory sessionFactoryTest() {
        log.info("create sessionFactory for test profile");
        log.info("db url: " + url);
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder().applySetting("jakarta.persistence.jdbc.driver", driver)
                                                    .applySetting("jakarta.persistence.jdbc.url", url)
                                                    .applySetting("jakarta.persistence.jdbc.user", user)
                                                    .applySetting("jakarta.persistence.jdbc.password", password)
                                                    .applySetting("hibernate.dialect", dialect)
                                                    .applySetting("hibernate.show_sql", showSql)
                                                    .applySetting("hibernate.format_sql", formatSql)
                                                    .applySetting("hibernate.hbm2ddl.auto", hbm2ddlAuto)
                                                    .build();

        return new MetadataSources(registry).addAnnotatedClass(User.class)
                                            .addAnnotatedClass(Session.class)
                                            .addAnnotatedClass(Location.class)
                                            .buildMetadata()
                                            .buildSessionFactory();
    }
}