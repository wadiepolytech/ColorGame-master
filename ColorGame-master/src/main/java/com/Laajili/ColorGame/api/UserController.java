package com.Laajili.ColorGame.api;

import com.Laajili.ColorGame.Model.User;
import com.Laajili.ColorGame.Service.UserService;
import com.Laajili.ColorGame.filter.CustomAuthorizationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class UserController {
    private final UserService userService;

    @GetMapping("/user/all")
    public ResponseEntity<List<User>>getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody User user) {
//        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
         userService.saveUser(user);
    }


    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String refresh_token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(refresh_token);
                    String username = decodedJWT.getSubject();
                    User user = userService.getUser(username);
                    String access_token = JWT.create()
                            .withSubject(user.getUsername())
                            .withExpiresAt(new Date(String.valueOf(LocalDate.now().plusDays(1))))
                            .withIssuer(request.getRequestURL().toString())
                            .withClaim("roles",user.getRole())
                            .sign(algorithm);

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token",access_token);
                    tokens.put("refresh_token",refresh_token);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),tokens);

                } catch (Exception exception) {
                    log.error("Error logging in : {} ",exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(FORBIDDEN.value());
//                    response.sendError(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message",exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),error);
                }
            }else {
                throw new RuntimeException("Refresh Token is missing");
            }
        }
}
