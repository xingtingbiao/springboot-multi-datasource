package com.cfexlib.datasource.parser;

import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class HttpRequestParser {
    public HttpRequestParser() {
    }

    private static class HttpRequestParserInstance {
        private static final HttpRequestParser PARSER = new HttpRequestParser();
    }

    public static HttpRequestParser getParser() {
        return HttpRequestParserInstance.PARSER;
    }

    @SuppressWarnings("unchecked")
    public String parsePath(HttpServletRequest request, String keyName) {
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return map.get(keyName);
    }

    public String parseQuery(HttpServletRequest request, String keyName) {
        return request.getParameter(keyName);
    }

    public String parseHeader(HttpServletRequest request, String keyName) {
        return request.getHeader(keyName);
    }

}
