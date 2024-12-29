package com.foodWorld.controller;

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

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam List<Long> itemIds,
            @RequestParam List<Integer> quantities,
            @RequestParam String tableNumber) {
        Order createdOrder = orderService.createOrder(itemIds, quantities, tableNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
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
    
//    @GetMapping("/tableStatus/{tableNumber}")
//    public ResponseEntity<Boolean> checkTableStatus(@PathVariable String tableNumber) {
//    	List<Order> todayOrders = orderService.getTodayOrders();
//    	if(todayOrders == null) {
//    		return ResponseEntity.ok(true);
//    	}
//    	
//    	boolean isTableFree = isTableFree = todayOrders.stream()
//                .filter(order -> tableNumber.equals(order.getTableNumber()))
//                .anyMatch(order -> "COMPLETED".equals(order.getOrderStatus()));
//
//        if (!isTableFree) {
//            return ResponseEntity.ok(false);  
//        }
//
//        return ResponseEntity.ok(true); 
//    }
    
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
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
