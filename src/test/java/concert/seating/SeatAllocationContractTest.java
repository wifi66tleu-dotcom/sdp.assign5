package concert.seating;

import concert.model.Seat;
import concert.support.Seats;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeatAllocationContractTest {

    static Stream<SeatAllocationStrategy> allStrategies() {
        return Stream.of(new BestViewAllocation(), new CheapestAllocation(), new TogetherAllocation());
    }

    @ParameterizedTest(name = "returns exactly the requested number of distinct free seats")
    @MethodSource("allStrategies")
    void returnsRequestedNumberOfDistinctFreeSeats(SeatAllocationStrategy strategy) {
        List<Seat> hall = Seats.grid(4, 12);
        List<Seat> result = strategy.allocate(hall, 5);
        assertEquals(5, result.size());
        assertEquals(5, new HashSet<>(result).size());
        assertTrue(hall.containsAll(result));
    }

    @ParameterizedTest(name = "returns an empty list when the hall is too small")
    @MethodSource("allStrategies")
    void returnsEmptyWhenHallIsTooSmall(SeatAllocationStrategy strategy) {
        assertTrue(strategy.allocate(Seats.grid(1, 3), 4).isEmpty());
    }

    @ParameterizedTest(name = "{0} returns an empty list for zero tickets")
    @MethodSource("allStrategies")
    void returnsEmptyForZeroTickets(SeatAllocationStrategy strategy) {
        assertTrue(strategy.allocate(Seats.grid(2, 5), 0).isEmpty());
    }
}
