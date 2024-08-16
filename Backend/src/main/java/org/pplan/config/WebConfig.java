package org.pplan.config;

import jakarta.servlet.Filter;
import org.pplan.filter.JwtFilter;
import org.pplan.util.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 웹 관련 설정을 정의하는 클래스
 * CORS 설정 및 JWT 필터 설정을 포함
 */
// 김동혁
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * JwtFilter 빈을 생성하는 메서드
     * @param jwtUtil JWT 유틸리티 클래스 인스턴스
     * @return 생성된 JwtFilter 인스턴스
     */
    @Bean
    public JwtFilter jwtFilter(JwtUtil jwtUtil) {
        return new JwtFilter(jwtUtil);
    }

    /**
     * JWT 필터를 등록하고 보호할 경로를 설정하는 메서드
     * @param jwtFilter JWT 필터 인스턴스
     * @return JWT 필터 등록 빈
     */
    @Bean
    public FilterRegistrationBean<Filter> jwtFilterRegistration(JwtFilter jwtFilter) {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/**"); // 모든 경로에 대해 JWT 필터를 적용
        return registrationBean;
    }

    /**
     * CORS 설정을 추가하는 메서드
     * @param registry CORS 레지스트리
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 설정
                .allowedOrigins("http://localhost:8000") // 허용할 오리진 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드 설정
                .allowedHeaders("*") // 모든 헤더 허용
                .exposedHeaders("X-Total-Count") // 클라이언트에서 접근할 수 있는 응답 헤더 설정
                .allowCredentials(true); // 인증 정보 허용 (쿠키, Authorization 헤더 등)
    }

    /**
     * CORS 필터를 등록하고 CORS 설정을 적용하는 메서드
     * @return CORS 필터 등록 빈
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8000"); // 허용할 오리진 설정
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        config.addExposedHeader("X-Total-Count"); // 클라이언트에서 접근할 수 있는 응답 헤더 설정
        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 CORS 설정 적용
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0); // 필터의 순서를 0으로 설정하여 가장 먼저 실행되도록 함
        return bean;
    }
}
