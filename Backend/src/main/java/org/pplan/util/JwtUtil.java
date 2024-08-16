package org.pplan.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

// 김동혁
@Component
@Slf4j
public class JwtUtil {

    // jwt.secret 값을 application.properties에서 가져와서 secret 변수에 할당
    @Value("${jwt.secret}")
    private String secret;

    // jwt.expiration 값을 application.properties에서 가져와서 jwtExpirationInMs 변수에 할당
    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    /**
     * 클래스 초기화 후 실행되는 메서드
     * JWT 서명에 사용할 secret 키를 Base64로 인코딩
     */
    @PostConstruct
    public void init() {
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    /**
     * 주어진 이메일을 기반으로 JWT 토큰을 생성
     * @param email 사용자 이메일
     * @return 생성된 JWT 토큰
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // 이메일을 토큰의 subject로 설정
                .setIssuedAt(new Date()) // 토큰 생성 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)) // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secret) // HS256 알고리즘을 사용해 서명
                .compact();
    }

    /**
     * JWT 토큰이 유효한지 검증
     * @param token JWT 토큰
     * @param email 사용자 이메일
     * @return 토큰이 유효하면 true, 그렇지 않으면 false
     */
    public boolean validateToken(String token, String email) {
        final String extractedEmail = extractEmail(token); // 토큰에서 이메일 추출
        return (extractedEmail.equals(email) && !isTokenExpired(token)); // 이메일이 일치하고 토큰이 만료되지 않았는지 확인
    }

    /**
     * JWT 토큰에서 이메일(subject)을 추출
     * @param token JWT 토큰
     * @return 추출된 이메일 (subject)
     */
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * JWT 토큰에서 만료 시간을 추출
     * @param token JWT 토큰
     * @return 토큰의 만료 시간
     */
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    /**
     * JWT 토큰이 만료되었는지 확인
     * @param token JWT 토큰
     * @return 토큰이 만료되었으면 true, 그렇지 않으면 false
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * JWT 토큰에서 모든 Claims를 추출
     * @param token JWT 토큰
     * @return 토큰의 Claims (페이로드 데이터)
     */
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret) // 토큰 서명에 사용된 키 설정
                .parseClaimsJws(token) // 토큰을 파싱하여 Claims 추출
                .getBody();
    }
}
