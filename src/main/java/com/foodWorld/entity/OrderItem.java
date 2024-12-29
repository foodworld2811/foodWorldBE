package com.foodWorld.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodWorld.entity.Order;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
//    @JsonIgnore
    private CategoryItems categoryItem;

//    @JsonIgnore
    private int quantity;
    
    public String getItemName() {
        return categoryItem != null ? categoryItem.getItemName() : null;
    }
    public String getItemPrice() {
        return categoryItem != null ? categoryItem.getItemPrice() : null;
    }
}
