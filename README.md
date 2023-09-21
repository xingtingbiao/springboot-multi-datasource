# springboot-datasource-config
## Modules Included
1. springboot-multi-datasource
2. springboot-mybatis-demo
3. springboot-jpa-demo
4. springboot-jdbc-demo

## Module Description
the core module of dynamic data source switching:springboot-multi-datasource, the other three modules correspond to the basic demo project of the integrated spring boot web server under different ORM frameworks(jdbc/jpa/mybatis).

## Supported Features(core module)
* Unified configuration of multiple datasources
* Dynamic routing by parameter
* Local transaction support
* Support of various ORM frameworks
  * spring-boot-starter-data-jdbc
  * spring-boot-starter-data-jpa
  * mybatis-spring-boot-starter(recommend)

## Application Framework

| Name       | Version | Note                                        |
|------------|---------|---------------------------------------------|
| Springboot | 2.7.x   | 2.7.x(tested), other versions are uncertain |


## Usage

### 1. Enable Multi DataSource
add annotation to xxxApplication.class
* @EnableJdbcMultiDataSource → spring-boot-starter-data-jdbc
* @EnableJpaMultiDataSource → spring-boot-starter-data-jpa
* @EnableMybatisMultiDataSource → mybatis-spring-boot-starter
:example(jdbc)
```java
@SpringBootApplication
@EnableJdbcMultiDataSource
public class SpringbootJdbcDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJdbcDemoApplication.class, args);
	}

}
```

:example(jpa)
```java
/*your own repository package path*/
@EnableJpaRepositories(basePackages = "com.cfex.jpa.repository")
@SpringBootApplication
@EnableJpaMultiDataSource
public class SpringbootJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

}
```

:example(mybatis)
```java
/*your own mapper package path*/
@MapperScan(basePackages = "com.cfex.mybatis.mapper")
@EnableMybatisMultiDataSource
@SpringBootApplication
public class SpringbootMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}

}

```

### 2. Dynamic Routing DataSource
add annotation to xxxController.class or method, @DataSourceRouter  <font class="text-color-1" color="#f44336">default value is true.</font>
#### Case 1: If you want to route all api under the controller, just add it to controller:

```java
@RestController
@RequestMapping(value = "demo")
@Slf4j
@AllArgsConstructor
@DataSourceRouter
public class UserController {
  private final IUserService userService;


  @GetMapping(value = "/get/{marketplaceCode}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User findById(@PathVariable("id") Integer id, @PathVariable("marketplaceCode") String marketplaceCode) {
    User byId = userService.findById(id);
    log.info("", byId);
    return byId;
  }

}

```

#### Case 2: If you just want to route one api under the controller, just add it to the api method:
```java

@RestController
@RequestMapping(value = "demo")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final IUserService userService;

    @DataSourceRouter
    @GetMapping(value = "/get/{marketplaceCode}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findById(@PathVariable("id") Integer id, @PathVariable("marketplaceCode") String marketplaceCode) {
        User byId = userService.findById(id);
        log.info("", byId);
        return byId;
    }

}

```

#### Case 3: If you just want to disable one api under the controller not others, like this:
```java

@RestController
@RequestMapping(value = "demo")
@Slf4j
@AllArgsConstructor
@DataSourceRouter
public class UserController {
    private final IUserService userService;

    @DataSourceRouter(value = false)
    @GetMapping(value = "/get/{marketplaceCode}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findById(@PathVariable("id") Integer id, @PathVariable("marketplaceCode") String marketplaceCode) {
        User byId = userService.findById(id);
        log.info("", byId);
        return byId;
    }

}

```


