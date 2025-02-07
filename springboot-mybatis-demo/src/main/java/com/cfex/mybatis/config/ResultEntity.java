package com.cfex.customer.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Stack;

import static com.cfex.customer.entity.constant.GlobalConstant.RequestId;

@Slf4j
@Data
@NoArgsConstructor(staticName = "of")
@Accessors(chain = true)
@Generated
public class ResultEntity<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "this a meta object")
    private ResultMeta meta;

    @Schema(description = "This is a paradigm object that the response result data")
    private T data;


    public ResultEntity<T> ok(HttpServletRequest request) {
        ResultMeta meta = ResultMeta.of()
                .setCode("OK")
                .setRequestId(request.getParameter(RequestId))
                .setRequestUrl(this.getRequestUrl(request))
                .setRequestBody(this.getRequestBody(request))
                .setPublicMessage("Request Success");
        return this.setMeta(meta);
    }

    public ResultEntity<T> error(HttpServletRequest request) {
        ResultMeta meta = ResultMeta.of()
                .setRequestId(request.getParameter(RequestId))
                .setRequestUrl(this.getRequestUrl(request))
                .setRequestBody(this.getRequestBody(request));
        return this.setMeta(meta);
    }

    public ResultEntity<T> exception(Exception e) {
        if (e instanceof BindException) {
            this.getMeta().setPublicMessage(((BindException) e).getAllErrors().getFirst().getDefaultMessage());
            this.getMeta().setPrivateMessage(((BindException) e).getAllErrors().getFirst().getDefaultMessage());
        } else {
            this.getMeta().setPublicMessage(e.getMessage());
            this.getMeta().setPrivateMessage(e.getMessage());
        }
        log.error(String.format("Request URL: %s", this.getMeta().getRequestUrl()), e);
        return this;
    }

    public ResultEntity<T> code(String code) {
        this.getMeta().setCode(code);
        return this;
    }

    public ResultEntity<T> async(boolean async) {
        this.getMeta().setAsyncFlag(async);
        return this;
    }

    public ResultEntity<T> requestBody(Object requestBody) {
        this.getMeta().setRequestBody(requestBody);
        return this;
    }

    public ResultEntity<T> publicMessage(String publicMessage) {
        this.getMeta().setPublicMessage(publicMessage);
        return this;
    }

    public ResultEntity<T> privateMessage(String privateMessage) {
        this.getMeta().setPrivateMessage(privateMessage);
        return this;
    }

    public ResultEntity<T> serviceErrorStack(Stack<StackError> serviceErrorStack) {
        this.getMeta().setServiceErrorStack(serviceErrorStack);
        return this;
    }

    public ResultEntity<T> data(T data) {
        return this.setData(data);
    }

    private String getRequestUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        if (request.getQueryString() != null)
            url.append("?").append(request.getQueryString());
        return url.toString();
    }

    private String getRequestBody(HttpServletRequest request) {
        String context = null;
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            context = new String(wrapper.getContentAsByteArray(), Charset.forName(wrapper.getCharacterEncoding()));
        }
        return context;
    }

    @Data
    @NoArgsConstructor(staticName = "of")
    @Accessors(chain = true)
    public static class ResultMeta implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @Schema(example = "OK", description = "allow OK/200, default:OK")
        private String code;

        @Schema(description = "api tracking id(UUID)")
        private String requestId;

        @Schema(description = "request url")
        private String requestUrl;

        @Schema(description = "This is a paradigm object that the request data")
        private Object requestBody;

        @Schema(description = "semantic message")
        private String publicMessage;

        @Schema(description = "base backend internal message")
        private String privateMessage;

        @Schema(description = "service error stack")
        Stack<StackError> serviceErrorStack = new Stack<>();

        @Schema(description = "async flag")
        boolean asyncFlag;
    }

    @Data
    @NoArgsConstructor(staticName = "of")
    @Accessors(chain = true)
    public static class StackError {
        private String metaCode;
        private String privateMessage;
        private String publicMessage;
    }

}
