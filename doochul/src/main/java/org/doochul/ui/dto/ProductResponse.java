package org.doochul.ui.dto;

import java.util.List;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductType;

public record ProductResponse(
        Long id,
        String name,
        ProductType type,
        String teacher,
        int count
) {
    public static ProductResponse from(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getType(), product.getTeacherName(),
                product.getCount());
    }

    public static List<ProductResponse> from(final List<Product> products) {
        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }
}
