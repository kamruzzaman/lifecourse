//package com.lifecourse.course_service.modules.order.application;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class CartService {
//    private final CartItemRepository cartItemRepository;
////    private final CourseRepository courseRepository;
//    private final OrderRepository orderRepository;
//
//    public ApiResponse getUserCart(String username) {
//        List<CartItemEntity> cartItems = cartItemRepository.findByUsername(username);
//        return new ApiResponse(200, "Cart retrieved", cartItems);
//    }
//
//    public ApiResponse addToCart(AddToCartRequest request) {
//        if (cartItemRepository.existsByUsernameAndCourse_Id(request.username(), request.courseId())) {
//            return new ApiResponse(409, "Course already in cart", null);
//        }
//        CourseEntity course = courseRepository.findById(request.courseId())
//                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
//
//        CartItemEntity item = new CartItemEntity();
//        item.setUsername(request.username());
//        item.setCourse(course);
//        item.setAddedAt(LocalDateTime.now());
//
//        cartItemRepository.save(item);
//        return new ApiResponse(201, "Course added to cart", item);
//    }
//
//    public ApiResponse removeFromCart(String username, Long courseId) {
//        cartItemRepository.deleteByUsernameAndCourse_Id(username, courseId);
//        return new ApiResponse(200, "Removed from cart", null);
//    }
//
//    public ApiResponse checkout(String username) {
//        List<CartItemEntity> cartItems = cartItemRepository.findByUsername(username);
//        if (cartItems.isEmpty()) {
//            return new ApiResponse(400, "Cart is empty", null);
//        }
//
//        OrderEntity order = new OrderEntity();
//        order.setUsername(username);
//        order.setStatus("PENDING");
//        order.setCreatedAt(LocalDateTime.now());
//
//        BigDecimal total = BigDecimal.ZERO;
//        for (CartItemEntity cartItem : cartItems) {
//            OrderItemEntity orderItem = new OrderItemEntity();
//            orderItem.setCourse(cartItem.getCourse());
//            orderItem.setOrder(order);
//            orderItem.setPrice(cartItem.getCourse().getPrice());
//            order.getItems().add(orderItem);
//            total = total.add(cartItem.getCourse().getPrice());
//        }
//
//        order.setTotalAmount(total);
//        OrderEntity savedOrder = orderRepository.save(order);
//        cartItemRepository.deleteByUsername(username);
//
//        return new ApiResponse(201, "Order created", savedOrder);
//    }
//}
//
