package com.cas.autoconfigure;

import com.cas.Interceptor.PageMybatisInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/6/8 9:24 上午
 * @desc
 */
@Configuration
@ConditionalOnProperty(prefix = "cas.mybatis-pagination", name = "enable", havingValue = "true")
public class PageAutoConfiguration {

    @Bean
    public PageMybatisInterceptor pageMybatisInterceptor() {
        return new PageMybatisInterceptor();
    }

}
