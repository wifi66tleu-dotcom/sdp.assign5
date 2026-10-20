package concert.seating;

import concert.model.Seat;
import java.util.List;
import java.util.Objects;

public class SeatAllocationContext {

    private SeatAllocationStrategy strategy;

    public SeatAllocationContext(SeatAllocationStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(SeatAllocationStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy");
    }

    public List<Seat> reserve(List<Seat> available, int quantity) {

        return strategy.allocate(available, quantity);
    }

    public String strategyName() {
        return strategy.name();
    }
}
