package com.cfex.jdbc.controller;

import com.cfexlib.datasource.route.DataSourceRouter;
import com.cfexlib.datasource.route.Location;
import com.cfex.jdbc.entity.User;
import com.cfex.jdbc.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "demo")
@Slf4j
@AllArgsConstructor
@DataSourceRouter
public class UserController {
    private final IUserService userService;


    @GetMapping(value = "/get/{marketplaceCode}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User demoDefaultRouter(@PathVariable("id") Integer id, @PathVariable("marketplaceCode") String marketplaceCode) {
        User user = userService.findById(id);
        log.info(user.toString());
        return user;
    }


    @DataSourceRouter(value = false)
    @GetMapping(value = "/get/disable/{marketplaceCode}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User demoWithoutRouter(@PathVariable("id") Integer id,
                                  @PathVariable("marketplaceCode") String marketplaceCode) {
        User user = userService.findById(id);
        log.info(user.toString());
        return user;
    }

    @DataSourceRouter(location = Location.HEADER)
    @GetMapping(value = "/get/header/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User demoHeaderRouter(@PathVariable("id") Integer id,
                                 @RequestHeader("marketplaceCode") String marketplaceCode) {
        User user = userService.findById(id);
        log.info(user.toString());
        return user;
    }

    @DataSourceRouter(location = Location.QUERY)
    @GetMapping(value = "/get/query/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User demoQueryRouter(@PathVariable("id") Integer id,
                                @RequestParam("marketplaceCode") String marketplaceCode) {
        User user = userService.findById(id);
        log.info(user.toString());
        return user;
    }

    @DataSourceRouter(keyName = "marketplaceId", location = Location.HEADER)
    @GetMapping(value = "/get/header/keyName/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User demoKeyNameRouter(@PathVariable("id") Integer id,
                                  @RequestHeader String marketplaceId) {
        User user = userService.findById(id);
        log.info(user.toString());
        return user;
    }

//    @DataSourceRouter(keyValue = "t1")
//    @GetMapping(value = "/get/header/keyValue/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public User demoKeyValueRouter(@PathVariable("id") Integer id,
//                                   @RequestHeader String marketplaceId) {
//        User byId = userService.findById(id);
//        log.info("", byId);
//        return byId;
//    }

    @DataSourceRouter(value = false)
    @GetMapping(value = "/timeout")
    public String testTimeout() throws InterruptedException {
        Thread.sleep(300000);
        return "hello world";
    }

}
