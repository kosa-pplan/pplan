package org.pplan.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.pplan.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JWT 토큰을 검증하는 필터 클래스
 * 모든 HTTP 요청에 대해 JWT 토큰의 유효성을 확인하여 인증을 처리
 */
// 김동혁
public class JwtFilter extends HttpFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * JwtUtil을 생성자 주입으로 받아서 초기화
     * @param jwtUtil JWT 유틸리티 클래스 인스턴스
     */
    // 생성자 주입
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 요청에 포함된 JWT 토큰을 검증하고, 유효하지 않으면 요청을 차단
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param chain 필터 체인 객체
     * @throws IOException 입출력 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 요청 헤더에서 Authorization 헤더를 가져옴
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 형식이 잘못된 경우 401 응답을 반환
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        // Bearer 토큰을 추출
        String token = authorizationHeader.substring(7);

        try {
            // JWT 토큰에서 클레임을 추출
            Claims claims = jwtUtil.extractClaims(token);

            // 클레임이 없거나 토큰이 만료된 경우 401 응답을 반환
            if (claims == null || jwtUtil.isTokenExpired(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Expired or invalid JWT token");
                return;
            }

            // JWT가 유효하면 필터 체인을 계속 진행
            request.setAttribute("claims", claims);
            chain.doFilter(request, response);

        } catch (Exception e) {
            // 토큰 검증 실패 시 401 응답을 반환
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT token validation failed");
        }
    }
}
