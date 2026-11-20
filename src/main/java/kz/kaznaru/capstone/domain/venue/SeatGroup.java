package kz.kaznaru.capstone.domain.venue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Composite pattern: composite node (venue, sector or row).
 */
public class SeatGroup extends SeatComponent {

    private static final long serialVersionUID = 1L;

    private final GroupKind kind;
    private final List<SeatComponent> children = new ArrayList<>();

    public SeatGroup(String name, GroupKind kind) {
        super(name);
        this.kind = kind;
    }

    public GroupKind getKind() {
        return kind;
    }

    public SeatGroup add(SeatComponent child) {
        children.add(child);
        return this;
    }

    public List<SeatComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public int totalSeats() {
        return children.stream().mapToInt(SeatComponent::totalSeats).sum();
    }

    @Override
    public int availableSeats() {
        return children.stream().mapToInt(SeatComponent::availableSeats).sum();
    }

    @Override
    public List<Seat> allSeats() {
        List<Seat> seats = new ArrayList<>();
        children.forEach(child -> seats.addAll(child.allSeats()));
        return seats;
    }

    @Override
    public Optional<Seat> findSeat(String seatId) {
        for (SeatComponent child : children) {
            Optional<Seat> found = child.findSeat(seatId);
            if (found.isPresent()) {
                return found;
            }
        }
        return Optional.empty();
    }

    @Override
    public void render(StringBuilder out, String indent) {
        if (kind == GroupKind.ROW) {
            out.append(indent).append(getName()).append(": ");
            children.forEach(child -> child.render(out, indent));
            out.append('\n');
            return;
        }
        out.append(indent).append(kind.label()).append(' ').append(getName())
                .append(" (").append(availableSeats()).append('/').append(totalSeats()).append(" бос)\n");
        children.forEach(child -> child.render(out, indent + "  "));
    }
}
