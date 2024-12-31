package com.foodWorld.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.foodWorld.entity.OrderItem;

@Entity
@Table(name = "orders")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;


    @Column(nullable = false)
    private LocalDate orderDate = LocalDate.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
    
    @Column(nullable = false)
    private String orderStatus = "ACTIVE";
    
    @Column(nullable = false)
    private String tableNumber;
    
    @Column(nullable = false)
    private String createdBy;
}