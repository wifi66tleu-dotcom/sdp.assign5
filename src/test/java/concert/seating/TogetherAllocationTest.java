package concert.seating;

import concert.model.Seat;
import concert.support.Seats;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TogetherAllocationTest {

    private final TogetherAllocation strategy = new TogetherAllocation();

    @Test
    void picksNeighbouringSeatsInOneRow() {
        List<Seat> result = strategy.allocate(Seats.grid(3, 10), 4);
        assertEquals(4, result.size());
        assertEquals(1, result.stream().map(Seat::row).distinct().count());
        for (int i = 1; i < result.size(); i++) {
            assertEquals(result.get(i - 1).number() + 1, result.get(i).number());
        }
    }

    @Test
    void picksTheCheapestBlock() {
        List<Seat> result = strategy.allocate(Seats.grid(3, 10), 4);
        assertTrue(result.stream().allMatch(seat -> seat.row() == 3));
    }

    @Test
    void skipsRowsWhereSeatsAreSplitByTakenSeats() {
        List<Seat> free = Seats.grid(3, 10).stream()
                .filter(seat -> seat.row() != 3 || seat.number() % 2 == 0)
                .toList();
        List<Seat> result = strategy.allocate(free, 3);
        assertTrue(result.stream().allMatch(seat -> seat.row() == 2));
    }

    @Test
    void returnsEmptyWhenNoBlockIsBigEnough() {
        List<Seat> broken = List.of(new Seat(1, 1, 100), new Seat(1, 3, 100), new Seat(2, 5, 100));
        assertTrue(strategy.allocate(broken, 2).isEmpty());
    }
}
