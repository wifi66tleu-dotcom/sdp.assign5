package concert.seating;

import concert.model.Seat;
import java.util.Comparator;
import java.util.List;

public class CheapestAllocation implements SeatAllocationStrategy {

    @Override
    public List<Seat> allocate(List<Seat> available, int quantity) {
        if (quantity <= 0 || available.size() < quantity) {
            return List.of();
        }
        return available.stream()
                .sorted(Comparator.comparingDouble(Seat::price)
                        .thenComparingInt(Seat::row)
                        .thenComparingInt(Seat::number))
                .limit(quantity)
                .toList();
    }

    @Override
    public String name() {
        return "Cheapest";
    }
}
