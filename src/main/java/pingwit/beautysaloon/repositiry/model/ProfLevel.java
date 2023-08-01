package pingwit.beautysaloon.repositiry.model;

import java.util.HashSet;
import java.util.Set;

public enum ProfLevel {
    BASIC("basic"),
    MIDDLE("middle"),
    SENIOR("senior");

    final String value;

    public String getValue() {
        return value;
    }

    ProfLevel(String value) {
        this.value = value;
    }

    private static final Set<String> VALUES = new HashSet<>();

    static {
        for (ProfLevel e: values()) {
            VALUES.add(e.value);
        }
    }

    public boolean hasValue(String value) {
        return VALUES.contains(value);
    }
}
