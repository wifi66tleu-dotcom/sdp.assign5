package concert.seating;

import concert.model.Seat;
import java.util.Comparator;
import java.util.List;

public class BestViewAllocation implements SeatAllocationStrategy {

    @Override
    public List<Seat> allocate(List<Seat> available, int quantity) {
        if (quantity <= 0 || available.size() < quantity) {
            return List.of();
        }
        double centre = centreLine(available);
        return available.stream()
                .sorted(Comparator.comparingInt(Seat::row)
                        .thenComparingDouble(seat -> Math.abs(seat.number() - centre)))
                .limit(quantity)
                .toList();
    }

    private double centreLine(List<Seat> seats) {
        int min = seats.stream().mapToInt(Seat::number).min().orElse(1);
        int max = seats.stream().mapToInt(Seat::number).max().orElse(1);
        return (min + max) / 2.0;
    }

    @Override
    public String name() {
        return "Best view";
    }
}
