package com.makeitlouder.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.makeitlouder.ui.model.response.InvalidLoginResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        InvalidLoginResponse loginResponse = new InvalidLoginResponse();

        ObjectMapper mapper = new ObjectMapper();
        String jsonLoginResponse = mapper.writeValueAsString(loginResponse);

        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().print(jsonLoginResponse);
    }
}
