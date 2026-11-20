package kz.kaznaru.capstone.domain.venue;

/** Seat category with a price multiplier in percent of the event base price. */
public enum SeatCategory {
    STANDARD(100),
    VIP(200);

    private final int priceMultiplierPercent;

    SeatCategory(int priceMultiplierPercent) {
        this.priceMultiplierPercent = priceMultiplierPercent;
    }

    public int priceMultiplierPercent() {
        return priceMultiplierPercent;
    }
}
