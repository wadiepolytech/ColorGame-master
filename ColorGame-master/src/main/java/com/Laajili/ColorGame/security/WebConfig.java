package com.Laajili.ColorGame.security;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//     @Override
//    public void addCorsMappings(CorsRegistry corsRegistry)
//     {
//          corsRegistry.addMapping("/**")
//                  .allowedOriginPatterns("http://localhost:4200")
//                  .allowedMethods("*")
//                  .maxAge(3600L)
//                  .allowedHeaders("*")
//                  .exposedHeaders("Authorization")
//                  .allowCredentials(true);
//     }
//}


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebConfig extends OncePerRequestFilter {

    private final Logger LOG = LoggerFactory.getLogger(WebConfig.class);

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        LOG.info("Adding CORS Headers ........................");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        res.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        if ("OPTIONS".equals(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }
}
