package kz.kaznaru.capstone.domain.venue;

public enum SeatStatus {
    AVAILABLE('o'),
    HELD('h'),
    SOLD('x');

    private final char symbol;

    SeatStatus(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }
}
