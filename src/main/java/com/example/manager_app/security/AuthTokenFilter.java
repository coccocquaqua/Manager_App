package com.example.manager_app.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        //lấy thông tin từ request
        //FilterChain giúp chuyển tiếp request đến các filter khác

        final String requestTokenHeader = request.getHeader("Authorization"); // lấy tiêu đề authorization từ request
        //
        String username = null;
        String jwtToken = null;
        //kiểm tra tiêu đề authotity có tồn tại và bắt đầu bằng bearer không
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                //lấy tên người dùng từ token
                username = jwtUtils.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        }
    //kiểm tra xem người dùng đã được xác thực và được đặt vào SecurityContextHolder chưa
        // không hợp lệ sẽ chuyển tiếp đến doFilter lọc tiếp theo
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //tìm kiếm thông tin người dùng  từ csdl
            UserDetails userDetails = this.userDetailServiceImpl.loadUserByUsername(username);
            System.out.println(":)))))"+userDetails.getAuthorities());
            //kiểm tra xem token có hợp lệ khoong
            if (jwtUtils.validateToken(jwtToken, userDetails)) {
                //tạo đối tượng với userdetail và danh sách quyền truy cập của người dùng
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                //đặt đối tượng vào SecurityContextHolder để xác thực
                //SecurityContextHolder chứa thông tin về người dùng đang được xác thực
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response); //chuyển tiếp request đến các filter khác
    }
}
