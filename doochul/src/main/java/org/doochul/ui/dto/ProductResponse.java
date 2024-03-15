package org.doochul.ui.dto;

import org.doochul.domain.product.ProductType;

public record ProductResponse(
        Long id,
        String name,
        ProductType type,
        Long userId,
        Integer count
){
}
