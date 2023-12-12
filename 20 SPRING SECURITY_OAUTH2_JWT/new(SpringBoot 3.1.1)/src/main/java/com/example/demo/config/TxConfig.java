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
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement        //트랜잭션 관리를 활성화,@Transactional 어노테이션이 부착된 메서드를 감지하고 트랜잭션 처리를 자동으로 수행
@EnableJpaRepositories
        (
                basePackages ="com.example.demo.domain.repository",
                transactionManagerRef = "jpaTransactionManager"
        )
public class TxConfig {
    @Autowired
    private HikariDataSource dataSource;

    //-----------------------------------
    //JPA TransactionManager
    //-----------------------------------
    @Bean(name="jpaTransactionManager")
    public JpaTransactionManager  jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setDataSource(dataSource);

        return transactionManager;
    }

    //-----------------------------------
    // 기본 DataSourceTransactionmanager
    //-----------------------------------
    @Bean(name="dataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
