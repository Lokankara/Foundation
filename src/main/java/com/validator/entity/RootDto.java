package com.validator.entity;

import lombok.Getter;

@Getter
public class RootDto {
    private final Long rootId;
    private final String value;

    public RootDto(final Root root) {
        this.rootId = root.getRootId() == null
                ? Long.valueOf(System.nanoTime())
                : root.getRootId();
        this.value = root.getRoot();

    }
}
