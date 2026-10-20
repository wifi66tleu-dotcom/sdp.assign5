package concert.seating;

import concert.model.CustomerType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class SeatAllocationSelectorTest {

    private final SeatAllocationSelector selector = new SeatAllocationSelector();

    @Test
    void rule1_vip_getsBestView_whateverThePartySize() {
        assertInstanceOf(BestViewAllocation.class, selector.select(1, CustomerType.VIP));
        assertInstanceOf(BestViewAllocation.class, selector.select(4, CustomerType.VIP));
        assertInstanceOf(BestViewAllocation.class, selector.select(10, CustomerType.VIP));
    }

    @Test
    void rule2_regularPartyOfTwoToSix_getsSeatsTogether() {
        assertInstanceOf(TogetherAllocation.class, selector.select(2, CustomerType.REGULAR));
        assertInstanceOf(TogetherAllocation.class, selector.select(6, CustomerType.REGULAR));
    }

    @Test
    void rule3_singleRegularVisitor_getsCheapest() {
        assertInstanceOf(CheapestAllocation.class, selector.select(1, CustomerType.REGULAR));
    }

    @Test
    void rule3_regularPartyAboveSix_getsCheapest() {
        assertInstanceOf(CheapestAllocation.class, selector.select(7, CustomerType.REGULAR));
    }
}
