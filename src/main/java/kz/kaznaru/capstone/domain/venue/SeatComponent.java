package kz.kaznaru.capstone.domain.venue;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Composite pattern: the common interface for a single {@link Seat} (leaf)
 * and a {@link SeatGroup} (venue, sector, row). Client code counts, searches
 * and prints the whole seat map without knowing the depth of the tree.
 */
public abstract class SeatComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    protected SeatComponent(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int totalSeats();

    public abstract int availableSeats();

    public abstract List<Seat> allSeats();

    public abstract Optional<Seat> findSeat(String seatId);

    /** Appends a text picture of this component to {@code out}. */
    public abstract void render(StringBuilder out, String indent);
}
