package com.hardware.service;

import com.support.dao.reposiotry.base.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * <br>
 *
 * @author sunzhongshuai
 */
@EntityScan("com.hardware.model")
@EnableJpaRepositories(basePackages = {"com.hardware.dao","com.support.dao"},repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@SpringBootApplication
public class HardwareApplication {
    public static void main(String [] args){
        SpringApplication.run(HardwareApplication.class,args);
    }
}
