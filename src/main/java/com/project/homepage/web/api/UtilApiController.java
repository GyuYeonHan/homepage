package com.project.homepage.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/util")
@RequiredArgsConstructor
public class UtilApiController {

    @Value("${origin}")
    private String origin;

    @GetMapping("origin")
    public String getOrigin() {
        return origin;
    }
}
