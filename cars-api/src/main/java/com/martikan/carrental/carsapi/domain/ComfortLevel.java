package com.martikan.carrental.carsapi.domain;

/**
 * Enum for {@link Car}'s comfort levels.
 */
public enum ComfortLevel {

    SUPERIOR ("S"),
    LIFESTYLE ("A"),
    GOOD ("B"),
    COMMON ("C"),
    BASIC ("D");

    public final String value;

    ComfortLevel(final String value) {
        this.value = value;
    }

}
