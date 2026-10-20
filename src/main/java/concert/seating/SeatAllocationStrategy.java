package concert.seating;
import concert.model.Seat;
import java.util.List;

public interface SeatAllocationStrategy {
    List<Seat> allocate(List<Seat> available, int quantity);
    String name();
}
