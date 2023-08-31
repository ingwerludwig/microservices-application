package com.javagrind.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BaseController {

    @GetMapping("/ok")
    public Object base() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "OK");
        return map;
    }
}
