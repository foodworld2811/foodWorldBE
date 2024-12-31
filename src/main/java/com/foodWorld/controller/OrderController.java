package com.foodWorld.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodWorld.entity.Order;
import com.foodWorld.entity.OrderItem;
import com.foodWorld.entity.OrderItemResponseDTO;
import com.foodWorld.service.OrderService;
import com.foodWorld.service.SendEmailService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private SendEmailService emailService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam List<Long> itemIds,
            @RequestParam List<Integer> quantities,
            @RequestParam String tableNumber,
            @RequestParam String createdBy) {
        Order createdOrder = orderService.createOrder(itemIds, quantities, tableNumber, createdBy);
        
        if(createdOrder != null) {
        	String subject = "Order Details for Table Number: " + tableNumber;
            String body = buildOrderDetailsEmail(createdOrder);

            // Send email
            emailService.sendEmail(subject, body);
        }
        
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }
    
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrderItems(
            @PathVariable Long orderId,
            @RequestParam List<Long> itemIds,
            @RequestParam List<Integer> quantities) {
        Order updatedOrder = orderService.updateOrderItems(orderId, itemIds, quantities);
        return ResponseEntity.ok(updatedOrder);
    }


    @PutMapping("/status/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String orderStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
   
    
    @GetMapping("/tableStatus/{tableNumber}")
    public ResponseEntity<Boolean> checkTableStatus(@PathVariable String tableNumber) {
        List<Order> todayOrders = orderService.getTodayOrders();

        // If no orders exist for today, all tables are free
        if (todayOrders == null || todayOrders.isEmpty()) {
            return ResponseEntity.ok(true);
        }

        // Check if any order for the given table is marked as COMPLETED
        boolean isTableFree = todayOrders.stream()
                .filter(order -> tableNumber.equals(order.getTableNumber()))
                .allMatch(order -> "COMPLETED".equals(order.getOrderStatus()));

        return ResponseEntity.ok(isTableFree);
    }

    
    @GetMapping("/items/{orderId}")
    public List<OrderItemResponseDTO> getOrderItems(@PathVariable long orderId) {
        List<OrderItem> orderItems = orderService.getAllOrderItems();
        return orderItems.stream()
                .filter(item -> item.getOrder().getOrderId() == orderId)
                .map(item -> {
                    OrderItemResponseDTO dto = new OrderItemResponseDTO();
                    dto.setCategoryName(item.getCategoryItem().getCategoryName());
                    dto.setQuantity(item.getQuantity());
                    dto.setItemId(item.getCategoryItem().getItemId());
                    dto.setItemImage(item.getCategoryItem().getItemImage());
                    dto.setItemName(item.getCategoryItem().getItemName());
                    dto.setItemPrice(item.getCategoryItem().getItemPrice());
                    dto.setItemStatus(item.getCategoryItem().isItemstatus());
                    dto.setTableNumber(item.getOrder().getTableNumber());
                    dto.setOrderId(orderId);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private String buildOrderDetailsEmail(Order order) {
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<h2>Order Details for Table Number: ").append(order.getTableNumber()).append("</h2>");
        emailBody.append("<p><strong>Order Date:</strong> ").append(order.getOrderDate()).append("</p>");
        emailBody.append("<h3>Items Ordered:</h3>");
        emailBody.append("<ul>");

        for (OrderItem orderItem : order.getOrderItems()) {
            emailBody.append("<li>")
                     .append(orderItem.getItemName())
                     .append(" : ").append(orderItem.getQuantity()).append("")
                     .append("</li>");
        }

        emailBody.append("</ul>");
        emailBody.append("<p>Thanks,</p>");
        emailBody.append("<p><strong>Your Restaurant Team</strong></p>");

        return emailBody.toString();
    }
    
    @GetMapping("/user/{userName}")
    public ResponseEntity<List<Order>> getTodayOrdersByUserName(@PathVariable String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        System.out.println("Get orders by user called: " + userName);

        // Retrieve today's orders
        List<Order> todayOrders = orderService.getTodayOrders();

        if (todayOrders == null || todayOrders.isEmpty()) {
            System.out.println("No orders found for today.");
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Filter orders by the given username and status 'ACTIVE'
        List<Order> userOrders = todayOrders.stream()
                .filter(order -> userName.equals(order.getCreatedBy()) && "ACTIVE".equals(order.getOrderStatus()))
                .collect(Collectors.toList());

        System.out.println("Active orders count by user " + userName + ": " + userOrders.size());
        return ResponseEntity.ok(userOrders);
    }



}
