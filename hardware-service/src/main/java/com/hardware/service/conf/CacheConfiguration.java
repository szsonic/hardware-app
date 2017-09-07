package com.hardware.service.conf;

import org.springframework.context.annotation.Configuration;

/**
 * @author 李世成
 * @email: lishicheng@innjia.com
 * @date: 2017/8/29
 */

/**
 * 先暂时关掉
 */
@Configuration
//@EnableCaching
public class CacheConfiguration {
    /*
     * ehcache 主要的管理器
     */
//    @Bean(name = "appEhCacheCacheManager")
//    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
//        return new EhCacheCacheManager (bean.getObject ());
//    }
//
//    /*
//     * 据shared与否的设置,Spring分别通过CacheManager.create()或new CacheManager()方式来创建一个ehcache基地.
//     */
//    @Bean
//    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
//        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
//        //cacheManagerFactoryBean.setConfigLocation();
//        cacheManagerFactoryBean.setShared (true);
//        return cacheManagerFactoryBean;
//    }
}
