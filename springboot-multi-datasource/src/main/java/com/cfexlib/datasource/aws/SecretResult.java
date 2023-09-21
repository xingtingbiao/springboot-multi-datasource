package com.cfexlib.datasource.aws;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SecretResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String engine;

    private String host;

    private String port;

    private String dbname;

    private String schema;

    private String username;

    private String password;

}
