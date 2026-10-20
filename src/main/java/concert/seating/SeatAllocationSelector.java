package concert.seating;

import concert.model.CustomerType;

public class SeatAllocationSelector {

    static final int TOGETHER_MIN_PARTY = 2;
    static final int TOGETHER_MAX_PARTY = 6;

    private final SeatAllocationStrategy bestView = new BestViewAllocation();
    private final SeatAllocationStrategy together = new TogetherAllocation();
    private final SeatAllocationStrategy cheapest = new CheapestAllocation();

    public SeatAllocationStrategy select(int quantity, CustomerType customerType) {
        if (customerType == CustomerType.VIP) {
            return bestView;
        }
        if (quantity >= TOGETHER_MIN_PARTY && quantity <= TOGETHER_MAX_PARTY) {
            return together;
        }
        return cheapest;
    }
}
