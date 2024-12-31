package com.foodWorld.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    public Order createOrder(List<Long> itemIds, List<Integer> quantities, String tableNumber, String createdBy) {
        if (itemIds.size() != quantities.size()) {
            throw new IllegalArgumentException("Item IDs and quantities must have the same size.");
        }

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("ACTIVE");
        order.setTableNumber(tableNumber);
        order.setCreatedBy(createdBy);
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

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));
    }
    
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    @Transactional
    public Order updateOrderItems(Long orderId, List<Long> itemIds, List<Integer> quantities) {
        if (itemIds.size() != quantities.size()) {
            throw new IllegalArgumentException("Item IDs and quantities must have the same size.");
        }

        // Fetch the existing order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        // Clear existing order items
        List<OrderItem> existingOrderItems = order.getOrderItems();
        if (existingOrderItems != null) {
            for (OrderItem item : existingOrderItems) {
                orderItemRepository.delete(item);
            }
        }

        // Add new order items
        List<OrderItem> newOrderItems = new ArrayList<>();
        for (int i = 0; i < itemIds.size(); i++) {
            Long itemId = itemIds.get(i);
            int quantity = quantities.get(i);

            Optional<CategoryItems> categoryItemOptional = categoryItemsRepository.findById(itemId);
            if (categoryItemOptional.isEmpty()) {
                throw new IllegalArgumentException("Invalid CategoryItem ID: " + itemId);
            }

            CategoryItems categoryItem = categoryItemOptional.get();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setCategoryItem(categoryItem);
            orderItem.setQuantity(quantity);

            newOrderItems.add(orderItemRepository.save(orderItem));
        }

        // Update the order's items and save
        order.setOrderItems(newOrderItems);
        return orderRepository.save(order);
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
    
    public List<Order> getTodayOrders() {
        LocalDate today = LocalDate.now();
        List<Order> todayOrders = orderRepository.findByOrderDate(today);
        return todayOrders;
    }
    
    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> listItems = orderItemRepository.findAll();
        return listItems;
    }
    
    public List<Order> getOrdersByUserName(String userName) {
        return orderRepository.findByCreatedBy(userName);
    }

}
