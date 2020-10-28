package com.xo.common.config;


import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@MapperScan({"com.xo.mapper"})
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * Map下划线自动转驼峰<br>
     * 文档：https://mp.baomidou.com/guide/faq.html#%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8-%E3%80%90map%E4%B8%8B%E5%88%92%E7%BA%BF%E8%87%AA%E5%8A%A8%E8%BD%AC%E9%A9%BC%E5%B3%B0%E3%80%91<br>
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return i -> i.setObjectWrapperFactory(new MybatisMapWrapperFactory());
    }
}