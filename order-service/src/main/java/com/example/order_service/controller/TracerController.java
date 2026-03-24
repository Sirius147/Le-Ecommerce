package com.example.order_service.controller;

import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TracerController {
    private final Tracer tracer;
    @GetMapping("/trace-test")
    public String traceTest() {
        log.info("tracer bean class={}", tracer.getClass().getName());
        log.info("currentSpan={}", tracer.currentSpan());
        return "ok";
    }
}
