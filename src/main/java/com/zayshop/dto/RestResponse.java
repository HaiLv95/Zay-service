package com.zayshop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class RestResponse<T> {
    private Integer currentPage;
    private Integer currentSize;
    private Integer totalPage;
    private Integer totalItem;
    private List<T> listData;

    public RestResponse(List<T> listData, Integer currentPage, Integer currentSize, Integer totalPage, Integer totalItem){
        this.listData = listData;
        this.currentPage = currentPage;
        this.currentSize = currentSize;
        this.totalPage = totalPage;
        this.totalItem = totalItem;
    }
}
