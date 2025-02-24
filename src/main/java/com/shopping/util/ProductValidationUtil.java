package com.shopping.util;

import com.shopping.exception.InvalidProductDataException;

public class ProductValidationUtil {
    public static void validatePriceAndStock(Integer price, Integer stock) {
        if (price != null && price <= 0) {
            String errorMessage = String.format("Invalid price: %d", price);
            throw new InvalidProductDataException(errorMessage);
        }
        if (stock != null && stock < 0) {
            String errorMessage = String.format("Invalid stock: %d", stock);
            throw new InvalidProductDataException(errorMessage);
        }
    }
}
