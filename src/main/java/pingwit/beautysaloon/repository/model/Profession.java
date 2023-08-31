package pingwit.beautysaloon.repository.model;

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

    public static Profession findByValue(String value) {
        Profession result = null;
        for (Profession day : values()) {
            if (day.getValue().equalsIgnoreCase(value)) {
                result = day;
                break;
            }
        }
        return result;
    }
}
