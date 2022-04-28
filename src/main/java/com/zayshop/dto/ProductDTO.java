package com.zayshop.dto;

import com.zayshop.entity.Category;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Integer id;

    private String name;

    private Boolean available = false;

    private String image;

    private String desc;

    private Integer size;

    private Double price;

    private Integer sell;

    private Integer quantity;

    private Integer categoryID;

    private String categoryName;
}
