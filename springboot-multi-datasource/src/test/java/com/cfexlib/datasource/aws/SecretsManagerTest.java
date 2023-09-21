package com.cfexlib.datasource.aws;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SecretsManagerTest {

    @Test
//    @Disabled
    void testGet() {
        String secretId = "dev/data/customer/CU";
//        Region region = Region.of("us-west-2");
        String stringSecret = SecretManagerTool.getStringSecret(secretId);
        JSONObject jsonSecret = SecretManagerTool.getJsonSecret(secretId);
        SecretResult classSecret = SecretManagerTool.getClassSecret(secretId);
        System.out.println(stringSecret);
    }
}
