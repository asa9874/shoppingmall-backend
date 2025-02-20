package com.shopping.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shopping.dto.ProductDTO;
import com.shopping.model.Product;
import com.shopping.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    
    public List<ProductDTO> getProductItems(int count){
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            return Collections.emptyList(); 
        }
    
        List<ProductDTO> productDTOList = productList.stream()
            .limit(count)
            .map(product -> ProductDTO.builder()
                .id(product.getId())        
                .name(product.getName())          
                .description(product.getDescription())   
                .image("http://localhost:8080/images/"+product.getImage())              
                .price(product.getPrice())            
                .stock(product.getStock())            
                .sellerName(product.getSeller().getMember().getNickname())    
                .build())
            .collect(Collectors.toList());
        return productDTOList;
    }


}
