package com.shopping.util;

import com.shopping.dto.Request.ProductUpdateRequestDto;
import com.shopping.model.Product;

public class ProductUpdateUtil {
    public static void updateProductFields(Product product, ProductUpdateRequestDto requestDto) {
        if (requestDto.getName() != null) {
            product.setName(requestDto.getName());
        }
        if (requestDto.getDescription() != null) {
            product.setDescription(requestDto.getDescription());
        }
        if (requestDto.getImage() != null) {
            product.setImage(requestDto.getImage());
        }
        if (requestDto.getCategory() != null) {
            product.setCategory(requestDto.getCategory());
        }
    }
}
