package com.cfex.jdbc.thymeleaf;

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
public class Param implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String imgUrl;

    private Student student;

    @Data
    @NoArgsConstructor(staticName = "of")
    @Accessors(chain = true)
    @ToString
    public static class Student {
        private String name;
    }

}
