package kz.kaznaru.capstone.domain.venue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import kz.kaznaru.capstone.support.Fixtures;

/** Composite: the same calls work on a seat, a row, a sector and the whole venue. */
class SeatMapCompositeTest {

    @Test
    void countsSeatsThroughWholeTree() {
        SeatGroup venue = Fixtures.smallVenue();

        assertEquals(14, venue.totalSeats());
        assertEquals(14, venue.availableSeats());
        SeatComponent sectorB = venue.getChildren().get(1);
        assertEquals(10, sectorB.totalSeats());
        assertEquals(1, venue.findSeat("A-1-1").orElseThrow().totalSeats());
    }

    @Test
    void availableCountDropsWhenLeafChanges() {
        SeatGroup venue = Fixtures.smallVenue();
        Seat seat = venue.findSeat("B-2-3").orElseThrow();

        seat.hold();

        assertEquals(13, venue.availableSeats());
        assertEquals(9, venue.getChildren().get(1).availableSeats());
        assertEquals(SeatStatus.HELD, seat.getStatus());
    }

    @Test
    void findSeatSearchesAllLevels() {
        SeatGroup venue = Fixtures.smallVenue();

        assertTrue(venue.findSeat("A-1-4").isPresent());
        assertFalse(venue.findSeat("Z-9-9").isPresent());
        assertEquals(SeatCategory.VIP, venue.findSeat("A-1-4").orElseThrow().getCategory());
        assertEquals(14, venue.allSeats().size());
    }

    @Test
    void renderShowsTreeAndSeatSymbols() {
        SeatGroup venue = Fixtures.smallVenue();
        venue.findSeat("A-1-1").orElseThrow().hold();
        venue.findSeat("A-1-2").orElseThrow().hold();
        venue.findSeat("A-1-2").orElseThrow().sell();

        StringBuilder out = new StringBuilder();
        venue.render(out, "");

        String picture = out.toString();
        assertTrue(picture.contains("Алаң Test Hall (12/14 бос)"), picture);
        assertTrue(picture.contains("Сектор A"), picture);
        assertTrue(picture.contains("1: hxoo"), picture);
    }

    @Test
    void seatRejectsWrongTransitions() {
        Seat seat = new Seat("X-1-1", SeatCategory.STANDARD);

        assertThrows(IllegalStateException.class, seat::sell);
        seat.hold();
        assertThrows(IllegalStateException.class, seat::hold);
    }
}
