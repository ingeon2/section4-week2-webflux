package com.codestates.coffee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v11/coffees")
public class CoffeeController {
    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") long coffeeId) throws InterruptedException {
        CoffeeResponseDto responseDto = new CoffeeResponseDto(coffeeId, "카페라떼", "CafeLattee", 4000);

        Thread.sleep(5000);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
