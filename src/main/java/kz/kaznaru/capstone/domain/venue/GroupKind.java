package kz.kaznaru.capstone.domain.venue;

public enum GroupKind {
    VENUE("Алаң"),
    SECTOR("Сектор"),
    ROW("Қатар");

    private final String label;

    GroupKind(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
