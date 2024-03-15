package org.doochul.ui.dto;

import org.doochul.domain.product.ProductType;

public record ProductSaveRequest(
        String name,
        ProductType type,
        Integer count
){
}
