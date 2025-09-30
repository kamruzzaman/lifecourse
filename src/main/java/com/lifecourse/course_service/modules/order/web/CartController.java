package com.lifecourse.course_service.modules.order.web;

import com.lifecourse.course_service.modules.order.config.ApiResponse;
import com.lifecourse.course_service.modules.order.web.dto.AddToCartRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
   // private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUserCart(@RequestParam String username) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody @Valid AddToCartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse> removeFromCart(@RequestParam String username, @RequestParam Long courseId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse> checkout(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
