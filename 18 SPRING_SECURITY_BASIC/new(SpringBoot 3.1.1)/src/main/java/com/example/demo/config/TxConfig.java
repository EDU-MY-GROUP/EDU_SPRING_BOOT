package com.example.demo.config;


import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement

public class TxConfig {
    @Autowired
    private HikariDataSource dataSource;

    @Autowired
    private HikariDataSource dataSource2;

    //JPA TransactionManager Settings
    @Primary
    @Bean(name="jpaTransactionManager")
    public JpaTransactionManager  jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    // DataSource용 Tx
    @Bean(name="dataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource2);
    }

    // 멀티 트랜잭션 추가 new
//    @Bean("multiTransactionManager")
//    public PlatformTransactionManager multiTransactionManager(@Qualifier("jpaTransactionManager") PlatformTransactionManager primaryTransactionManager,
//                                                              @Qualifier("dataSourceTransactionManager") PlatformTransactionManager secondaryTransactionManager) {
//        return new ChainedTransactionManager(primaryTransactionManager, secondaryTransactionManager);
//    }




}
