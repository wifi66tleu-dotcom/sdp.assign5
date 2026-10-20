package concert.seating;

import concert.model.Seat;
import concert.support.Seats;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheapestAllocationTest {

    private final CheapestAllocation strategy = new CheapestAllocation();

    @Test
    void takesLowestPricedSeats() {
        List<Seat> result = strategy.allocate(Seats.grid(3, 10), 3);
        assertTrue(result.stream().allMatch(seat -> seat.price() == 100.0));
    }

    @Test
    void doesNotCareAboutNeighbours() {
        List<Seat> scattered = List.of(new Seat(3, 1, 100), new Seat(3, 5, 100), new Seat(3, 9, 100));
        assertEquals(3, strategy.allocate(scattered, 3).size());
    }

    @Test
    void returnsEmptyWhenNotEnoughSeats() {

        assertTrue(strategy.allocate(Seats.grid(1, 2), 3).isEmpty());
    }
}
