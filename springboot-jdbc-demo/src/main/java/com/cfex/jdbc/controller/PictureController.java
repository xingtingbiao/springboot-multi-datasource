package com.cfex.jdbc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "demo")
@Slf4j
@AllArgsConstructor
public class PictureController {

    @GetMapping(value = "/logo")
    public void logo(HttpServletResponse response) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/static/images/logo.jpeg");
        InputStream inputStream = classPathResource.getInputStream();
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = response.getOutputStream();
        int len = 0;
        byte[] buffer = new byte[4096];
        while ((len = inputStream.read(buffer)) != -1)
        {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
    }
}
