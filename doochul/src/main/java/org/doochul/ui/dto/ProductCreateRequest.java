package org.doochul.ui.dto;

import org.doochul.domain.product.ProductType;

public record ProductCreateRequest(
        String name,
        ProductType type,
        Integer count
) {
}
