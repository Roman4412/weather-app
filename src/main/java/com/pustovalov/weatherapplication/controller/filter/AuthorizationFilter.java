package com.pustovalov.weatherapplication.controller.filter;

import com.pustovalov.weatherapplication.entity.Session;
import com.pustovalov.weatherapplication.service.SessionService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Order(1)
public class AuthorizationFilter implements Filter {

    public static final String SESSION_COOKIE_NAME = "SESSIONID";

    private final SessionService sessionService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException,
            IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Set<String> protectedUris = Set.of("/locations", "/locations/weather", "/users/logout");

        if (protectedUris.contains(req.getRequestURI())) {

            Optional<Session> optionalSession = findSession(req.getCookies());

            if (optionalSession.isEmpty() || !sessionService.isValid(optionalSession.get())) {
                resp.sendRedirect("/users/login");
                return;
            }

            String userId = optionalSession.get().getUser().getId().toString();
            req.setAttribute("userId", userId);
            req.setAttribute("sessionId", optionalSession.get().getId());
        }

        chain.doFilter(req, resp);
    }

    private Optional<Session> findSession(Cookie[] cookies) {
        if (cookies == null) {
            return Optional.empty();
        }

        Optional<Cookie> optionalSession = Arrays.stream(cookies)
                .filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName()))
                .findFirst();
        if (optionalSession.isEmpty()) {
            return Optional.empty();
        }

        String sessionId = optionalSession.get().getValue();
        return sessionService.findBy(UUID.fromString(sessionId));
    }
}