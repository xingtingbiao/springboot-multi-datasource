package com.cfex.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "demo")
//@Tag(name = "demo")
public class DemoController {

    @GetMapping(value = "hello")
    public String hello(@RequestParam String name) {
        return "hello, I am " + name;
    }

}
