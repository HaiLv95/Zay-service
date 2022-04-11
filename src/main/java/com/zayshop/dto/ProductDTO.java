package com.zayshop.dto;

import com.zayshop.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDTO {

    private Integer id;

    private String name;

    private Boolean available = false;

    private String desc;

    private Integer size;

    private Double price;

    private Integer sell;

    private Integer quantity;

    private Integer categoryID;
}
