package zhurenko.ua.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("zhurenko.ua.repository")
@EnableTransactionManagement
public class PersistenceConfig  {

    //Подключение БД
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource =new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/books?serverTimezone=UTC&amp;autoReconnect=true&amp;useSSL = use");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

//    //Подключение Hibernate
//
//    public Properties hibernateProperties(){
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.show.sql", "true");
//        properties.setProperty("format_sql", "true");
//        properties.setProperty("use_sql_comments", "false");
//        properties.setProperty("hibernate.hbm2ddl.auto", "update");
//        return properties;
//    }
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactoryBean(){
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource());
//        sessionFactoryBean.setHibernateProperties(hibernateProperties());
//        sessionFactoryBean.setPackagesToScan("zhurenko.ua.model");
//        return sessionFactoryBean;
//    }

    //Подключение JPA
    @Bean
    public HibernateJpaVendorAdapter vendorAdapter(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        //vendorAdapter.setGenerateDdl(true);
        //vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter());
        entityManagerFactory.setPackagesToScan("zhurenko.ua.model");
        return  entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
}
