package com.cfex.jdbc.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://localhost:8083/demo", name = "test")
public interface OpenfeignTest {

    @GetMapping(path = "/timeout")
    String testTimeOut();

}
