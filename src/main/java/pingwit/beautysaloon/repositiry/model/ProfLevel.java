package pingwit.beautysaloon.repositiry.model;

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

    public static ProfLevel findByValue(String value) {
        ProfLevel result = null;
        for (ProfLevel day : values()) {
            if (day.getValue().equalsIgnoreCase(value)) {
                result = day;
                break;
            }
        }
        return result;
    }
}
