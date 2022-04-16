package com.zayshop.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {
    private  Integer id;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
