package com.codestates;

import com.codestates.coffee.CoffeeResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;

@Slf4j
@SpringBootApplication
public class SpringMvcMainSampleApplication {
	private final RestTemplate restTemplate;

	public SpringMvcMainSampleApplication(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcMainSampleApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return (String... args) -> {
			log.info("# 요청 시작 시간: {}", LocalTime.now());

			for (int i = 1; i <= 5; i++) {
				CoffeeResponseDto response = this.getCoffee();
				log.info("{}: coffee name: {}", LocalTime.now(), response.getKorName());
			}
		};
	}

	private CoffeeResponseDto getCoffee() {
		String uri = "http://localhost:8080/v11/coffees/1";
		ResponseEntity<CoffeeResponseDto> response = restTemplate.getForEntity(uri, CoffeeResponseDto.class);

		return response.getBody();
	}
}

//메인 애플리케이션 Controller(SpringMvcMainCoffeeController)의 getCoffee() 핸들러 메서드를 호출하는 클라이언트를 구현한 코드
//(원래는) 서비스와 리파지토리 로직을 사용해서 핸들러매서드 사용하는데, 여기는 간단구현을 위해 없으니.
//우리가 PostMan에서 하던 로직을 여기서 행하는것.