package com.dnd.global.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dnd.global.config.resolver.LoginUserDetailsResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final LoginUserDetailsResolver loginUserDetailsResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(loginUserDetailsResolver);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		String[] webEndpoints = {
				"http://localhost:3000"
		};

		Stream<String> stream = Stream.of();

		stream = Stream.concat(stream, Arrays.stream(webEndpoints));

		String[] endPoints = stream.toArray(String[]::new);

		registry.addMapping("/**")
				.allowedOrigins(endPoints)
				.allowedMethods("GET", "POST", "PUT", "OPTION", "HEAD", "DELETE");
	}
}
