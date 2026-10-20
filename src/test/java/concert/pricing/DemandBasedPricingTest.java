package concert.pricing;

import concert.support.FixedSalesDataSource;
import concert.support.Requests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DemandBasedPricingTest {

    @Test
    void emptyHall_costsBasePrice() {
        DemandBasedPricing pricing = new DemandBasedPricing(new FixedSalesDataSource(0.0));
        assertEquals(200.0, pricing.calculateTotal(Requests.of(2, 100, 10)), 1e-6);
    }

    @Test
    void halfFullHall_adds25Percent() {
        DemandBasedPricing pricing = new DemandBasedPricing(new FixedSalesDataSource(0.5));
        assertEquals(250.0, pricing.calculateTotal(Requests.of(2, 100, 10)), 1e-6);
    }

    @Test
    void soldOutHall_doublesThePrice() {
        DemandBasedPricing pricing = new DemandBasedPricing(new FixedSalesDataSource(1.0));
        assertEquals(400.0, pricing.calculateTotal(Requests.of(2, 100, 10)), 1e-6);
    }

    @Test
    void asksTheDataSourceAboutTheRequestedEvent() {
        FixedSalesDataSource fake = new FixedSalesDataSource(0.3);
        new DemandBasedPricing(fake).calculateTotal(Requests.of(1, 100, 10));
        assertEquals("rock-fest", fake.lastEventId());
    }

    @Test
    void rejectsMissingDataSource() {

        assertThrows(NullPointerException.class, () -> new DemandBasedPricing(null));
    }
}
