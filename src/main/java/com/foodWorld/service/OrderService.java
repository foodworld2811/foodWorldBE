package com.foodWorld.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodWorld.entity.CategoryItems;
import com.foodWorld.entity.Order;
import com.foodWorld.entity.OrderItem;
import com.foodWorld.repository.CategoryItemsRepository;
import com.foodWorld.repository.OrderItemRepository;
import com.foodWorld.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryItemsRepository categoryItemsRepository;

    @Transactional
    public Order createOrder(List<Long> itemIds, List<Integer> quantities) {
        if (itemIds.size() != quantities.size()) {
            throw new IllegalArgumentException("Item IDs and quantities must have the same size.");
        }

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("ACTIVE");
        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < itemIds.size(); i++) {
            Long itemId = itemIds.get(i);
            int quantity = quantities.get(i);

            Optional<CategoryItems> categoryItemOptional = categoryItemsRepository.findById(itemId);
            if (categoryItemOptional.isEmpty()) {
                throw new IllegalArgumentException("Invalid CategoryItem ID: " + itemId);
            }

            CategoryItems categoryItem = categoryItemOptional.get();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setCategoryItem(categoryItem);
            orderItem.setQuantity(quantity);

            orderItems.add(orderItemRepository.save(orderItem));
        }

        savedOrder.setOrderItems(orderItems);
        return orderRepository.save(savedOrder);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));
    }
    
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    @Transactional
    public Order updateOrderStatus(Long orderId, String orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        orderRepository.delete(order);
    }
}
