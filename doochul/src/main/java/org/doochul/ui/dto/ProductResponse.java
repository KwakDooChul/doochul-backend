package org.doochul.ui.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductType;
import org.doochul.domain.user.User;

public record ProductResponse(
        Long id,
        String name,
        ProductType type,
        User user,
        int count,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getType(), product.getTeacher(),
                product.getCount(), product.getCreatedAt(), product.getUpdatedAt());
    }

    public static List<ProductResponse> toResponse(List<Product> products) {
        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }
}
