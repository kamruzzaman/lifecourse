package com.lifecourse.course_service.application.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUtils {
    private SecurityUtils() {
        // utility class, prevent instantiation
    }
    public  static CurrentUser getCurrentUser(){
        return new CurrentUser("system",Set.of("Admin"));
    }
//    public static CurrentUser getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return null;
//        }
//
//        String username;
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof UserDetails userDetails) {
//            username = userDetails.getUsername();
//        } else if (principal instanceof String name && !name.isBlank()) {
//            username = name;
//        } else {
//            username = null;
//        }
//
//        Set<String> roles = authentication.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toSet());
//
//        return new CurrentUser(username, roles);
//    }

    // DTO for convenience
    public record CurrentUser(String username, Set<String> roles) { }
}
