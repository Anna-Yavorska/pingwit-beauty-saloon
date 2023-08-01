package pingwit.beautysaloon.exception;

import java.util.List;

public class ValidationException extends RuntimeException {
    private final List<String> violations;

    public ValidationException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }

    public List<String> getViolations() {
        return violations;
    }

    private String violationsToString(List<String> violations) {
        StringBuilder oneBigViolation = new StringBuilder("\nViolations:");
        for (String violation : violations) {
            oneBigViolation.append("\n").append(violation);
        }
        return oneBigViolation.toString();
    }

    @Override
    public String toString() {
        return getMessage() + violationsToString(violations);
    }
}
