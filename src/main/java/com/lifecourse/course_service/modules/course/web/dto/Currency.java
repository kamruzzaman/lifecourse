package com.lifecourse.course_service.modules.course.web.dto;

public enum Currency {
    BDT("BDT", "Bangladeshi Taka", "৳"),
    USD("USD", "United States Dollar", "$"),
    GBP("GBP", "British Pound Sterling", "£"),
    INR("INR", "Indian Rupee", "₹");

    private final String code;     // 3-letter code
    private final String fullName; // Full currency name
    private final String symbol;   // Currency symbol

    Currency(String code, String fullName, String symbol) {
        this.code = code;
        this.fullName = fullName;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return code + " (" + symbol + ")";
    }

    // Optional helper: lookup by code
    public static Currency fromCode(String code) {
        for (Currency c : values()) {
            if (c.code.equalsIgnoreCase(code)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid currency code: " + code);
    }
}
