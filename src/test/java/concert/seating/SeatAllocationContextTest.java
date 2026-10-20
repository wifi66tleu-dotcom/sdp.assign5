package concert.seating;

import concert.model.Seat;
import concert.support.Seats;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeatAllocationContextTest {

    @Test
    void delegatesToTheStrategy() {
        SeatAllocationContext context = new SeatAllocationContext(new BestViewAllocation());
        assertEquals(1, context.reserve(Seats.grid(3, 10), 2).get(0).row());
    }

    @Test
    void setStrategy_changesTheResultAtRuntime() {
        List<Seat> hall = Seats.grid(3, 10);
        SeatAllocationContext context = new SeatAllocationContext(new BestViewAllocation());
        int frontRow = context.reserve(hall, 2).get(0).row();

        context.setStrategy(new CheapestAllocation());
        int cheapRow = context.reserve(hall, 2).get(0).row();

        assertEquals(1, frontRow);
        assertEquals(3, cheapRow);
        assertEquals("Cheapest", context.strategyName());
    }

    @Test
    void rejectsNullStrategy() {

        assertThrows(NullPointerException.class, () -> new SeatAllocationContext(null));
    }
}
