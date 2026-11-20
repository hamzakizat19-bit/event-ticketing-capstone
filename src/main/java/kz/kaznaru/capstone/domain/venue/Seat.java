package kz.kaznaru.capstone.domain.venue;

import java.util.List;
import java.util.Optional;

/** Composite pattern: leaf. One physical seat, e.g. {@code A-2-7} (sector A, row 2, seat 7). */
public class Seat extends SeatComponent {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final SeatCategory category;
    private SeatStatus status = SeatStatus.AVAILABLE;

    public Seat(String id, SeatCategory category) {
        super(id);
        this.id = id;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public SeatCategory getCategory() {
        return category;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public boolean isAvailable() {
        return status == SeatStatus.AVAILABLE;
    }

    public void hold() {
        requireStatus(SeatStatus.AVAILABLE, "hold");
        status = SeatStatus.HELD;
    }

    public void sell() {
        requireStatus(SeatStatus.HELD, "sell");
        status = SeatStatus.SOLD;
    }

    private void requireStatus(SeatStatus expected, String action) {
        if (status != expected) {
            throw new IllegalStateException("Cannot " + action + " seat " + id + " in status " + status);
        }
    }

    @Override
    public int totalSeats() {
        return 1;
    }

    @Override
    public int availableSeats() {
        return isAvailable() ? 1 : 0;
    }

    @Override
    public List<Seat> allSeats() {
        return List.of(this);
    }

    @Override
    public Optional<Seat> findSeat(String seatId) {
        return id.equals(seatId) ? Optional.of(this) : Optional.empty();
    }

    @Override
    public void render(StringBuilder out, String indent) {
        out.append(status.symbol());
    }
}
