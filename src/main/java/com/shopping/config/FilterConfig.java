package com.shopping.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.shopping.filter.ExceptionCatchingFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ExceptionCatchingFilter> exceptionCatchingFilter() {
        FilterRegistrationBean<ExceptionCatchingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ExceptionCatchingFilter());
        registrationBean.addUrlPatterns("/*"); // 모든 URL에 적용
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 최우선 적용
        return registrationBean;
    }
}
