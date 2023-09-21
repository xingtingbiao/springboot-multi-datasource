package com.cfex.test.entity;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor(staticName = "of")
@Accessors(chain = true)
@ToString
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    @PostConstruct
    public void init(){
        System.out.println("init...");
    }

}
