package com.example.SmartEventManagementSystem.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.web.csrf.CsrfToken;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class CsrfController {

    @GetMapping("/csrf")
    public Map<String, String> csrf(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        Map<String, String> body = new HashMap<>();
        if (token != null) {
            body.put("token", token.getToken());
            body.put("parameterName", token.getParameterName());
            body.put("headerName", token.getHeaderName());
        } else {
            body.put("token", "");
            body.put("parameterName", "_csrf");
            body.put("headerName", "X-CSRF-TOKEN");
        }
        return body;
    }
}

