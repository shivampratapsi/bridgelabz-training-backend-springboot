package com.fundoo.fundoonotes.security;

import com.fundoo.fundoonotes.service.RedisService;
import com.fundoo.fundoonotes.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        String email = null;
        String token = null;
        try{
            // so do this first — if no token it throws IllegalArgumentException
            email = tokenUtil.extractEmailFromRequest(request);

            // only reach here if token exists
            String authHeader = request.getHeader("Authorization");
            token = authHeader.substring(7);

    } catch (IllegalArgumentException e) {
        // no token — register/login request, just move forward
        filterChain.doFilter(request, response);
        return;

    } catch (ExpiredJwtException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Token has expired, please login again");
        return;

    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Invalid token");
        return;
    }
//        String authHeader = request.getHeader("Authorization");

        //  bcz. this method run for every http request from user
//        if the request is for signup or register than there is no token at starting
//        and it will throw NLP  , But below method we already checked it in extractEmailFromRequest()
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//        String token = authHeader.substring(7);
//
//        String email = tokenUtil.extractEmailFromRequest(request);


//            token = authHeader.substring(7);
//
//

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //here we check redis has token or not

            String savedToken = "";
//            String fetchTokenFromRedis =redisService.fetchToken(email).getData();
            if (redisService.fetchToken(email).getData() != null) {

                savedToken = redisService.fetchToken(email).getData().toString();

            } else {
                savedToken = null;
            }

            if (savedToken != null && savedToken.equals(token)) {

//by default standard credential , that'w why pass null here
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            else {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is expred");
                return;
            }
        }

        filterChain.doFilter(request, response);

    }
}