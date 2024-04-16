package com.example.manager_app.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.security.Keys;
@Component
public class JwtUtils {

    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);
    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${bezkoder.app.jwtExpirationRefresh}")
    private int jwtExpirationRefresh;
    @Value("${bezkoder.app.jwtCookieName}")
    private String jwtCookie;
    //lấy khóa để tạo và xác thực mã thông báo jwwt
    //
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    //tạo token cho người dunng sau khi đăng nhập thành công
    //tạo ra 1 jwt token từ đối tượng userdetail
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationRefresh))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    //kiểm tra token có hợp lệ không
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token); //láy thông tin ng dùng từ token
       //so sánh usernamw do token trả về với username đối tượng userdetail cung cấp
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    //trả về coolie trống để cóa jwwt từ trình duyệt
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, "").path("/api").maxAge(0).build();
        return cookie;
    }
//lấy thông tin về người dùng từ token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    // hàm trích xuất thông tin cụ thể từ jwt token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
    //kiểm tra xem  token co hợp lệ hay không
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //kểm tra xem token hết hạn hay chưa
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}
