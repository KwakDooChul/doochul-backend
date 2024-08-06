package org.doochul.domain.product;

import java.util.Arrays;
import org.doochul.common.exception.BackEndApplicationException;
import org.doochul.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public enum ProductType {
    LOL, TFT;

    public static ProductType from(final String name) {
        return Arrays.stream(ProductType.values())
                .filter(value -> value.name().equals(name))
                .findFirst()
                .orElseThrow(
                        () -> new BackEndApplicationException(ErrorCodes.PRODUCT_TYPE_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
