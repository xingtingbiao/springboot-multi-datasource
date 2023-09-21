package com.cfex.jdbc.util;

import com.cfex.jdbc.dao.CustomerDao;
import com.cfex.jdbc.entity.Customer;
import com.cfex.jdbc.entity.User;
import com.cfex.jdbc.service.IUserService;
import com.cfexlib.datasource.tool.DataSourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyThread implements Runnable {

    private static IUserService userService;

//    @Autowired
    public MyThread(IUserService userService) {
        MyThread.userService = userService;
    }

    public MyThread() {
    }

    @Override
    public void run() {
        System.out.println("Current thread:" + Thread.currentThread().getName());
//        test1();
//        test2();
        test3();
    }

    private void test1() {
        User user = userService.findById(1);
        System.out.println(user);
    }

    private void test2() {
        User user = BeanContextUtil.getBean(IUserService.class).findById(1);
        System.out.println(user);
    }

    private void test3() {
        DataSourceManager dataSourceManager = BeanContextUtil.getBean(DataSourceManager.class);
        dataSourceManager.routing("CU");
        CustomerDao customerDao = (CustomerDao) BeanContextUtil.getBean("customerDao");
        Customer customer = customerDao.findById(1);
        System.out.println(customer);
    }

}
