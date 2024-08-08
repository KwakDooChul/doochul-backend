package org.doochul.ui.dto;

public record ProductUpdateRequest (
        String name,
        String type,
        Integer count
){
}
