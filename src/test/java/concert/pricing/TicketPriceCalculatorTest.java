package concert.pricing;

import concert.support.FixedSalesDataSource;
import concert.support.Requests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketPriceCalculatorTest {

    private static final double DELTA = 1e-6;

    private TicketPriceCalculator calculatorWithDemand(double soldRatio) {
        return new TicketPriceCalculator(new FixedSalesDataSource(soldRatio));
    }

    @Test
    void standard_isBasePriceTimesQuantity() {
        assertEquals(300.0, calculatorWithDemand(0).calculate(PricingType.STANDARD, Requests.of(3, 100, 10)), DELTA);
    }

    @Test
    void earlyBird_sixtyDaysOrMore_gives25PercentOff() {
        assertEquals(225.0, calculatorWithDemand(0).calculate(PricingType.EARLY_BIRD, Requests.of(3, 100, 60)), DELTA);
    }

    @Test
    void earlyBird_thirtyToFiftyNineDays_gives15PercentOff() {
        assertEquals(255.0, calculatorWithDemand(0).calculate(PricingType.EARLY_BIRD, Requests.of(3, 100, 40)), DELTA);
    }

    @Test
    void earlyBird_underThirtyDays_givesNoDiscount() {
        assertEquals(300.0, calculatorWithDemand(0).calculate(PricingType.EARLY_BIRD, Requests.of(3, 100, 29)), DELTA);
    }

    @Test
    void demandBased_emptyHall_costsBasePrice() {
        assertEquals(200.0, calculatorWithDemand(0.0).calculate(PricingType.DEMAND_BASED, Requests.of(2, 100, 10)), DELTA);
    }

    @Test
    void demandBased_halfFull_adds25Percent() {
        assertEquals(250.0, calculatorWithDemand(0.5).calculate(PricingType.DEMAND_BASED, Requests.of(2, 100, 10)), DELTA);
    }

    @Test
    void demandBased_soldOut_doublesThePrice() {
        assertEquals(400.0, calculatorWithDemand(1.0).calculate(PricingType.DEMAND_BASED, Requests.of(2, 100, 10)), DELTA);
    }

    @Test
    void groupBundle_everyFourthTicketIsFree() {
        TicketPriceCalculator calc = calculatorWithDemand(0);
        assertEquals(300.0, calc.calculate(PricingType.GROUP_BUNDLE, Requests.of(3, 100, 10)), DELTA);
        assertEquals(300.0, calc.calculate(PricingType.GROUP_BUNDLE, Requests.of(4, 100, 10)), DELTA);
        assertEquals(600.0, calc.calculate(PricingType.GROUP_BUNDLE, Requests.of(8, 100, 10)), DELTA);
    }
}
