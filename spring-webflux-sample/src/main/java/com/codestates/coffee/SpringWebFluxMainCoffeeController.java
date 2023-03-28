package com.codestates.coffee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/v11/coffees")
public class SpringWebFluxMainCoffeeController {
    String uri = "http://localhost:5050/v11/coffees/1";

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{coffee-id}")
    public Mono<CoffeeResponseDto> getCoffee(@PathVariable("coffee-id") long coffeeId) {
        log.info("# call Spring WebFlux Main Controller: {}", LocalDateTime.now());

        //외부 애플리케이션에 한번 더 요청을 전송(아웃바운드 커피 컨트롤러)
        return WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CoffeeResponseDto.class);
    }
}

//Spring WebFlux 기반의 메인 애플리케이션 Controller
//MVC 기반과의 차이점
//Spring MVC 기반 애플리케이션에서는 외부 애플리케이션과의 통신을 위해 RestTemplate을 사용한 반면
//Spring WebFlux 기반 애플리케이션에서는 위와 같이 WebClient라는 Rest Client를 사용

//두 번째 차이점은 getCoffee() 핸들러 메서드의 리턴 타입이 ResponseEntity<CoffeeResponseDto> 아니라 Mono<CoffeeResponseDto>