package com.cgnial.salesreports.domain;

import lombok.Getter;

@Getter
public enum Distributors {

    SATAU("Satau"),
    UNFI("UNFI"),
    PURESOURCE("Puresource"),
    AVRIL("Avril");

    private final String distributors;

    Distributors(String distributors) {
        this.distributors = distributors;
    }
}
