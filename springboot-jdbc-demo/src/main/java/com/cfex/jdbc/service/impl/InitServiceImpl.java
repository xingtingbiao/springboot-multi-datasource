package com.cfex.jdbc.service.impl;

import com.cfex.jdbc.util.MyThread;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class InitServiceImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("this implements CommandLineRunner init...");
        new Thread(new MyThread()).start();
    }

}
