package com.jobsprout.gateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网关全局异常处理器（WebFlux 响应式）。
 * 捕获网关层面的所有异常（服务不可用、超时、限流等），统一返回 JSON 格式并打印日志。
 */
@Slf4j
@Order(-1)
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private final List<HttpMessageReader<?>> messageReaders;
    private final List<HttpMessageWriter<?>> messageWriters;
    private final List<ViewResolver> viewResolvers;

    public GatewayExceptionHandler(List<HttpMessageReader<?>> messageReaders,
                                   List<HttpMessageWriter<?>> messageWriters,
                                   List<ViewResolver> viewResolvers) {
        this.messageReaders = messageReaders;
        this.messageWriters = messageWriters;
        this.viewResolvers = viewResolvers;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerRequest request = ServerRequest.create(exchange, messageReaders);
        Mono<ServerResponse> responseMono = handleException(request, ex);

        return responseMono.flatMap(response -> {
            exchange.getResponse().setStatusCode(response.statusCode());
            exchange.getResponse().getHeaders().putAll(response.headers());
            return response.writeTo(exchange, new ServerResponse.Context() {
                @Override
                public List<HttpMessageWriter<?>> messageWriters() {
                    return messageWriters;
                }

                @Override
                public List<ViewResolver> viewResolvers() {
                    return viewResolvers;
                }
            });
        });
    }

    private Mono<ServerResponse> handleException(ServerRequest request, Throwable ex) {
        String path = request.path();
        HttpStatus status;
        String message;
        int code;

        if (ex instanceof ResponseStatusException rse) {
            status = (HttpStatus) rse.getStatusCode();
            message = rse.getReason() != null ? rse.getReason() : status.getReasonPhrase();
            code = status.value();
            log.warn("网关响应异常：path={}, status={}, message={}", path, status, message);
        } else if (ex instanceof org.springframework.cloud.gateway.support.NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = "服务不存在或未找到";
            code = 404;
            log.warn("网关服务未找到：path={}, message={}", path, ex.getMessage());
        } else if (ex.getCause() instanceof java.net.ConnectException
                || ex.getMessage() != null && ex.getMessage().contains("Connection refused")) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            message = "服务暂时不可用，请稍后重试";
            code = 503;
            log.error("网关连接失败：path={}, message={}", path, ex.getMessage());
        } else if (ex instanceof java.util.concurrent.TimeoutException
                || ex.getMessage() != null && ex.getMessage().contains("timeout")) {
            status = HttpStatus.GATEWAY_TIMEOUT;
            message = "服务响应超时，请稍后重试";
            code = 504;
            log.error("网关超时：path={}, message={}", path, ex.getMessage());
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "网关内部错误";
            code = 500;
            log.error("网关未知异常：path={}", path, ex);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("code", code);
        body.put("message", message);
        body.put("data", null);
        body.put("timestamp", System.currentTimeMillis());

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body));
    }
}
