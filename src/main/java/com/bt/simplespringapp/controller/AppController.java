package com.bt.simplespringapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class AppController {

    private final JdbcTemplate jdbcTemplate;

    public AppController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Hello from Spring Boot + MySQL");
        return response;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            Map<String, String> ok = new LinkedHashMap<>();
            ok.put("status", "ok");
            ok.put("database", "connected");
            return ResponseEntity.ok(ok);
        } catch (Exception ex) {
            Map<String, String> error = new LinkedHashMap<>();
            error.put("status", "degraded");
            error.put("database", "disconnected");
            error.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
        }
    }
}
