package com.cfex.customer.config.response;

import com.cfex.customer.entity.response.ResultEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return null != returnType.getMethod() && null != returnType.getMethod().getDeclaringClass().getAnnotation(RestController.class)
                && !returnType.getMethod().getDeclaringClass().getName().contains("springdoc")
                && !returnType.getParameterType().equals(ResultEntity.class)
                && !returnType.getMethod().getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)
                && !returnType.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ResultEntity<Object> result = ResultEntity.of()
                .ok(((ServletServerHttpRequest) request).getServletRequest())
                .data(body);

        return !returnType.getParameterType().equals(String.class) ? result
//                : JSON.toJSONString(result, JSONWriter.Feature.WriteNulls);
                : new ObjectMapper().writeValueAsString(result);
    }
}
