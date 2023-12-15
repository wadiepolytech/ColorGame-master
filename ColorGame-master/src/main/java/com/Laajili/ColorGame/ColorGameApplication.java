package com.Laajili.ColorGame;

import com.Laajili.ColorGame.Model.User;
import com.Laajili.ColorGame.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
public class ColorGameApplication {

	public static void main(String[] args) {

		SpringApplication.run(ColorGameApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("http://localhost:4200");
////		config.setAllowedOriginPatterns(Arrays.asList("*"));
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("OPTIONS");
//		config.addAllowedMethod("GET");
//		config.addAllowedMethod("POST");
//		config.addAllowedMethod("PUT");
//		config.addAllowedMethod("DELETE");
//		config.addAllowedMethod(HttpMethod.POST);
//		source.registerCorsConfiguration("/**", config);
//		return new CorsFilter(source);
//	}

//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//		config.setAllowedHeaders(Arrays.asList("*"));
//		config.setExposedHeaders(Arrays.asList("*"));
//		config.setAllowedMethods(Arrays.asList("*"));
//		source.registerCorsConfiguration("/**", config);
//
//		return new CorsFilter(source);
//	}
	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.saveUser(new User(null,"mohamed.laajili@polytechnicien.tn","50185440","50185440","USER"));
		};
	}

}
