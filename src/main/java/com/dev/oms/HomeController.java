package com.dev.oms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
public class HomeController {

    @GetMapping("home")
    public ZonedDateTime get() {
        return ZonedDateTime.now();
    }
}
