package concert.pricing;

import concert.support.FixedSalesDataSource;
import concert.support.Requests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PricingSelectorTest {

    private PricingSelector selectorWithDemand(double soldRatio) {
        return new PricingSelector(new FixedSalesDataSource(soldRatio));
    }

    @Test
    void rule1_groupOfFourOrMore_getsGroupBundle() {
        assertInstanceOf(GroupBundlePricing.class, selectorWithDemand(0.1).select(Requests.of(4, 100, 10)));
        assertInstanceOf(GroupBundlePricing.class, selectorWithDemand(0.1).select(Requests.of(9, 100, 10)));
    }

    @Test
    void rule1_beatsHighDemandAndEarlyBird() {
        assertInstanceOf(GroupBundlePricing.class, selectorWithDemand(0.95).select(Requests.of(4, 100, 90)));
    }

    @Test
    void rule2_highDemand_getsDemandBased() {
        assertInstanceOf(DemandBasedPricing.class, selectorWithDemand(0.8).select(Requests.of(2, 100, 10)));
        assertInstanceOf(DemandBasedPricing.class, selectorWithDemand(1.0).select(Requests.of(1, 100, 10)));
    }

    @Test
    void rule2_justBelowThreshold_isNotDemandBased() {
        assertInstanceOf(StandardPricing.class, selectorWithDemand(0.79).select(Requests.of(2, 100, 10)));
    }

    @Test
    void rule2_beatsEarlyBird() {
        assertInstanceOf(DemandBasedPricing.class, selectorWithDemand(0.9).select(Requests.of(2, 100, 90)));
    }

    @Test
    void rule3_thirtyDaysOrMore_getsEarlyBird() {
        assertInstanceOf(EarlyBirdPricing.class, selectorWithDemand(0.2).select(Requests.of(2, 100, 30)));
        assertInstanceOf(EarlyBirdPricing.class, selectorWithDemand(0.2).select(Requests.of(2, 100, 120)));
    }

    @Test
    void rule4_otherwise_getsStandard() {
        assertInstanceOf(StandardPricing.class, selectorWithDemand(0.2).select(Requests.of(2, 100, 29)));
        assertInstanceOf(StandardPricing.class, selectorWithDemand(0.2).select(Requests.of(3, 100, 0)));
    }
}
