package com.cfexlib.datasource.aws;

import com.alibaba.fastjson.JSONObject;
import com.cfexlib.datasource.exception.AWSSecretAccessException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;

public class SecretManagerTool {
    private static volatile SecretsManagerClient client;

    public static String getStringSecret(String secretId) {
        try {
            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                    .secretId(secretId)
                    .build();
            GetSecretValueResponse getSecretValueResponse = getClient().getSecretValue(getSecretValueRequest);
            return getSecretValueResponse.secretString();
        } catch (Exception e) {
            String msg = String.format("secretId:%s -> %s", secretId, e);
            throw new AWSSecretAccessException(msg);
        }
    }

    public static JSONObject getJsonSecret(String secretId) {
        return JSONObject.parseObject(getStringSecret(secretId));
    }

    public static SecretResult getClassSecret(String secretId) {
        return JSONObject.parseObject(getStringSecret(secretId), SecretResult.class);
    }

    public static String selfDefineSecretId(String env, String team, String app, String keyName) {
        return String.format("%s/%s/%s/%s", env, team, app, keyName);
    }

    public static SecretsManagerClient getClient() {
        if (null == client) {
            synchronized (SecretManagerTool.class) {
                if (null == client) {
                    client = SecretsManagerClient.builder().build();
                }
            }
        }
        return client;
    }

}
