package com.zayshop.service;

import com.zayshop.dto.ProductDTO;
import com.zayshop.dto.RestResponse;
import com.zayshop.entity.Category;
import com.zayshop.entity.Product;
import com.zayshop.repository.CategoryRepository;
import com.zayshop.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product deleteProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findByID(Integer id) {
        return productRepository.findById(id);
    }

    public ProductDTO findProduct(Integer id){
        Optional<Product> optional = productRepository.findById(id);
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(optional.get(), dto);
        dto.setCategoryID(optional.get().getCategoryID().getId());
        dto.setCategoryName(optional.get().getCategoryID().getName());
        return  dto;
    }

    public RestResponse<ProductDTO> getViewShop(Integer page, Integer size, String productName, Integer categoryID) throws Exception {
        Pageable pageable = PageRequest.of((page -1), size);
        Page<Product> pageData = productRepository.searchAll(productName, categoryID, pageable);
        List<ProductDTO> listDTO =  pageData.toList().stream()
                .map(item -> ProductDTO.builder()
                        .id(item.getId())
                        .categoryID(item.getCategoryID().getId())
                        .available(item.getAvailable())
                        .desc(item.getDesc())
                        .name(item.getName())
                        .price(item.getPrice())
                        .sell(item.getSell())
                        .quantity(item.getQuantity())
                        .image(item.getImage())
                        .size(item.getSize())
                        .build()).collect(Collectors.toList());
        RestResponse<ProductDTO> response = new RestResponse<ProductDTO>(listDTO, page, size, pageData.getTotalPages(), (int)pageData.getTotalElements());
        return response;
    }
}
