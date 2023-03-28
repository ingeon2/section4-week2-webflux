package com.codestates.coffee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/v11/coffees")
public class SpringMvcMainCoffeeController {
    private final RestTemplate restTemplate;
    //클라이언트의 요청을 메인 애플리케이션에서 직접 처리하는 것이 아니라 Spring의 Rest Client인 RestTemplate을 이용해서 외부에 있는 다른 애플리케이션에게 한번 더 요청을 전송하는 것

    String uri = "http://localhost:7070/v11/coffees/1";

    public SpringMvcMainCoffeeController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") long coffeeId) {
        log.info("# call Spring MVC Main Controller: {}", LocalDateTime.now());
        ResponseEntity<CoffeeResponseDto> response = restTemplate.getForEntity(uri, CoffeeResponseDto.class);
        return ResponseEntity.ok(response.getBody());
    }
}

//Spring MVC와 Spring WebFlux의 요청 처리 방식을 비교하기 위한 목적이기때문에 다른 요청 핸들러가 없음.
//
//그리고 별도의 데이터베이스와의 연동도 없으니 이 점을 염두에 두고 코드를 확인