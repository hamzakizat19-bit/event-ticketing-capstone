package kz.kaznaru.capstone.support;

import kz.kaznaru.capstone.domain.venue.GroupKind;
import kz.kaznaru.capstone.domain.venue.Seat;
import kz.kaznaru.capstone.domain.venue.SeatCategory;
import kz.kaznaru.capstone.domain.venue.SeatGroup;

/** Shared test data. */
public final class Fixtures {

    private Fixtures() {
    }

    /** Sector A: VIP, 1 row x 4 seats. Sector B: standard, 2 rows x 5 seats. 14 seats total. */
    public static SeatGroup smallVenue() {
        SeatGroup venue = new SeatGroup("Test Hall", GroupKind.VENUE);
        venue.add(sector("A", SeatCategory.VIP, 1, 4));
        venue.add(sector("B", SeatCategory.STANDARD, 2, 5));
        return venue;
    }

    private static SeatGroup sector(String name, SeatCategory category, int rows, int seatsPerRow) {
        SeatGroup sector = new SeatGroup(name, GroupKind.SECTOR);
        for (int r = 1; r <= rows; r++) {
            SeatGroup row = new SeatGroup(String.valueOf(r), GroupKind.ROW);
            for (int s = 1; s <= seatsPerRow; s++) {
                row.add(new Seat(name + "-" + r + "-" + s, category));
            }
            sector.add(row);
        }
        return sector;
    }
}
