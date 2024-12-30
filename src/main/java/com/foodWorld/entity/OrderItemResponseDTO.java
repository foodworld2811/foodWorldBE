package com.foodWorld.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDTO {

	private String categoryName;
    private int quantity;
    private long itemId;
    private byte[] itemImage;
    private String itemName;
    private String itemPrice;
    private boolean itemStatus;
    private String tableNumber;
    private long orderId;
}
