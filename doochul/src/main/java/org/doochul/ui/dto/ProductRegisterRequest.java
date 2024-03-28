package org.doochul.ui.dto;

import org.doochul.domain.product.ProductType;

public record ProductRegisterRequest(
        String name,
        ProductType type,
        Integer count
) {
}
