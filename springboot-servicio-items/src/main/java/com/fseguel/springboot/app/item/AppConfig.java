package com.fseguel.springboot.app.item;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
//@EnableSwagger2
public class AppConfig {
	
	@Bean("clienteRest")
	@LoadBalanced
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory -> factory.configureDefault( id -> {
			return new Resilience4JConfigBuilder(id)
					.circuitBreakerConfig(CircuitBreakerConfig.custom()
							.slidingWindowSize(10)
							.failureRateThreshold(50)
							.waitDurationInOpenState(Duration.ofSeconds(10L))
							.permittedNumberOfCallsInHalfOpenState(5)
							.slowCallRateThreshold(50) //  porcentaje llamadas lentas
							.slowCallDurationThreshold(Duration.ofSeconds(2L)) // duracion considerada llamada lenta
							.build())
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3L)).build())
					.build();
					
		});
	}
	
	/*@Bean
	public Docket initSwagger() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.fseguel.springboot.app.item.controlles"))
				.paths(PathSelectors.any())
				.build();
	}*/
	
}
