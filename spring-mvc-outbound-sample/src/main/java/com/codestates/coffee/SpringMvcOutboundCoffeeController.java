package com.codestates.coffee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v11/coffees")
public class SpringMvcOutboundCoffeeController {
    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") long coffeeId) throws InterruptedException {
        CoffeeResponseDto responseDto = new CoffeeResponseDto(coffeeId, "카페라떼", "CafeLattee", 4000);
        //Spring MVC 애플리케이션의 요청 처리 방식을 확인하는 것이 주목적이기 때문에
        //별도의 데이터베이스 연동 없이 단순히 Stub 데이터(responseDto)를 응답으로 넘겨줌.

        Thread.sleep(5000);
        return ResponseEntity.ok(responseDto);
    }
}

//메인 애플리케이션 Controller(SpringMvcMainCoffeeController) 의 getCoffee() 핸들러 메서드에서
//RestTemplate으로 전송한 요청을 전달 받는 외부 애플리케이션의 Controller(SpringMvcOutboundCoffeeController) 코드