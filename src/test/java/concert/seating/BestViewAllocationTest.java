package concert.seating;

import concert.model.Seat;
import concert.support.Seats;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BestViewAllocationTest {

    private final BestViewAllocation strategy = new BestViewAllocation();

    @Test
    void takesFrontRowFirst() {
        List<Seat> result = strategy.allocate(Seats.grid(3, 10), 3);
        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(seat -> seat.row() == 1));
    }

    @Test
    void prefersSeatsNearTheCentreLine() {
        List<Seat> result = strategy.allocate(Seats.grid(3, 10), 2);
        assertTrue(result.stream().anyMatch(seat -> seat.number() == 5));
        assertTrue(result.stream().anyMatch(seat -> seat.number() == 6));
    }

    @Test
    void movesToNextRowWhenFrontRowIsFull() {
        List<Seat> free = Seats.grid(3, 10).stream().filter(seat -> seat.row() != 1).toList();
        assertTrue(strategy.allocate(free, 2).stream().allMatch(seat -> seat.row() == 2));
    }

    @Test
    void returnsEmptyWhenNotEnoughSeats() {

        assertTrue(strategy.allocate(Seats.grid(1, 3), 4).isEmpty());
    }
}
