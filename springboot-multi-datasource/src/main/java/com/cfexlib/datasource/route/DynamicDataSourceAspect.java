package com.cfexlib.datasource.route;

import com.cfexlib.datasource.DataSourceContextHolder;
import com.cfexlib.datasource.parser.HttpRequestParser;
import com.cfexlib.datasource.tool.DataSourceManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
@Aspect
@Order(1)
@Component
@EnableConfigurationProperties(RouterProperties.class)
public class DynamicDataSourceAspect {

    @Resource
    private RouterProperties routerProperties;

    @Resource
    private DataSourceManager dataSourceManager;

    // todo @Autowired Tenant mapper to get Tenant info
//    @Pointcut("execution(* com.cfex..controller.*.*(..))")
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) " +
            "|| @within(org.springframework.stereotype.Controller)")
    private void pointCut() {}


    @Before("pointCut()")
    public void switchDataSource(JoinPoint joinPoint) {
        if (this.routerProperties.isEnable()) {
            DataSourceRouter router = this.dynamicRoutingSwitch(joinPoint);
            if (null != router && router.value()) {
                this.routingDataSource(router);
            }
        } else {
            log.debug("datasource.router.enable=false, it won't routing datasource.");
        }
    }


    @After("pointCut()")
    public void restoreDataSource() {
        DataSourceContextHolder.clearDataSourceKey();
    }

    private DataSourceRouter dynamicRoutingSwitch(JoinPoint joinPoint) {
        Class<?> aClass = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
//        log.info("cutting point: " + aClass.getName() + "." + method.getName() + "()...");
        DataSourceRouter classAnnotation = aClass.getAnnotation(DataSourceRouter.class);
        DataSourceRouter methodAnnotation = method.getAnnotation(DataSourceRouter.class);
        if (null == classAnnotation) {
            return methodAnnotation;
        } else {
            return Objects.requireNonNullElse(methodAnnotation, classAnnotation);
        }
    }

    /**
     * V1 use secret manager to get db info
     * @param router self define annotation
     */
    private void routingDataSource(DataSourceRouter router) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String keyValue = this.getKeyValue(request, router);
        dataSourceManager.routing(router, keyValue);
    }

    /**
     * get tenant info such as ${marketplaceCode} from HttpServletRequest(pass type:path/query/header)
     * @param request HttpServletRequest
     * @param router DataSourceRouter annotation
     * @return tenant info such as ${marketplaceCode}
     */
    private String getKeyValue(HttpServletRequest request, DataSourceRouter router) {
        if (StringUtils.hasLength(router.keyValue())) return router.keyValue();
        return switch (router.location()) {
            case QUERY -> HttpRequestParser.getParser().parseQuery(request, router.keyName());
            case HEADER -> HttpRequestParser.getParser().parseHeader(request, router.keyName());
            default -> HttpRequestParser.getParser().parsePath(request, router.keyName());
        };
    }

}
