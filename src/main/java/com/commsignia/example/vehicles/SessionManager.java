package com.commsignia.example.vehicles;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@Component
public class SessionManager extends OncePerRequestFilter {

    private static final String SESSION_COOKIE_NAME = "sessionId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Check if the session ID is present in cookies
        String sessionId = getSessionIdFromCookies(request);
        if (sessionId == null) {
            // If not present, generate a new session ID
            sessionId = generateSessionId();
            // Set the session ID in cookies
            addSessionIdToCookies(sessionId, response);
        }
        // Add the session ID to the request attributes for further processing
        request.setAttribute("sessionId", sessionId);
        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }

    private String getSessionIdFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SESSION_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void addSessionIdToCookies(String sessionId, HttpServletResponse response) {
        Cookie sessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}