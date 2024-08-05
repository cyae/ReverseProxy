package com.example.reverseproxy.Service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class ProxyServer {

    private static final int PORT = 8181;

    @PostConstruct
    public void init() {
        Vertx.vertx().deployVerticle(new ProxyServerVerticle(PORT));
    }

    public static class ProxyServerVerticle extends AbstractVerticle {
        private int port;

        public ProxyServerVerticle(int port) {
            this.port = port;
        }

        @Override
        public void start() throws Exception {
            vertx.createHttpServer().requestHandler(req -> {
                Future<Buffer> body = req.body();
                body.onSuccess(buffer -> {
                    String s = new String(buffer.getBytes(), StandardCharsets.UTF_8);
                    log.info("Proxy server received {}", s);
                    req.response().putHeader("content-type", "text/plain").end(s);
                }).onFailure(e -> log.error("Proxy server error", e));
            }).listen(port, res -> {
                if (res.succeeded()) {
                    log.info("Proxy server started on port {}", port);
                } else {
                    log.error("Proxy server error", res.cause());
                }
            });
        }
    }
}
