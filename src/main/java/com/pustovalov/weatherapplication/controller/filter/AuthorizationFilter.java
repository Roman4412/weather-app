package com.pustovalov.weatherapplication.controller.filter;

import com.pustovalov.weatherapplication.entity.Session;
import com.pustovalov.weatherapplication.service.SessionService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Order(1)
@Slf4j
public class AuthorizationFilter implements Filter {

    public static final String SESSION_COOKIE_NAME = "SESSIONID";

    private final SessionService sessionService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException,
            IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Optional<Session> optionalSession = findValidSession(req.getCookies());

        if ("/weather".equals(req.getRequestURI()) || "/location".equals(req.getRequestURI())) {
            if (optionalSession.isPresent()) {
                String userId = optionalSession.get().getUser().getId().toString();
                req.setAttribute("userId", userId);
                req.setAttribute("sessionId", optionalSession.get().getId());
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect("/login");
            }
        } else if ("/login".equals(req.getRequestURI()) || "/registration".equals(req.getRequestURI())) {
            if (optionalSession.isPresent()) {
                String userId = optionalSession.get().getUser().getId().toString();
                req.setAttribute("userId", userId);
                request.getRequestDispatcher("/weather").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private Optional<Session> findValidSession(Cookie[] cookies) {
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