package pingwit.beautysaloon.repositiry.model;

import java.util.HashSet;
import java.util.Set;

public enum Profession {
    HAIRDRESSER("hairdresser"),
    MANICURIST("manicurist"),
    BROW_DESIGNER("brow designer");
    final String value;

    public String getValue() {
        return value;
    }

    Profession(String value) {
        this.value = value;
    }

    private static final Set<String> VALUES = new HashSet<>();

    static {
        for (Profession e : values()) {
            VALUES.add(e.value);
        }
    }

    public boolean hasValue(String value) {
        return VALUES.contains(value);
    }
}
