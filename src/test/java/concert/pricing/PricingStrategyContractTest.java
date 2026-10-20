package concert.pricing;
import concert.model.CustomerType;
import concert.model.TicketRequest;
import concert.support.FixedSalesDataSource;
import concert.support.Requests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PricingStrategyContractTest {

    static Stream<PricingStrategy> allStrategies() {
        FixedSalesDataSource sales = new FixedSalesDataSource(0.6);
        return Stream.of(
                new StandardPricing(),
                new EarlyBirdPricing(),
                new DemandBasedPricing(sales),
                new GroupBundlePricing());
    }

    static List<TicketRequest> sampleRequests() {
        return List.of(
                Requests.of(1, 0, 0),
                Requests.of(1, 100, 0),
                Requests.of(4, 99.99, 45),
                Requests.of(50, 1000, 365),
                new TicketRequest("e", 3, 20, LocalDate.of(2026, 5, 1), LocalDate.of(2026, 5, 1), CustomerType.VIP));
    }

    @ParameterizedTest(name = "never returns a negative price")
    @MethodSource("allStrategies")
    void price_isNeverNegative(PricingStrategy strategy) {
        for (TicketRequest request : sampleRequests()) {
            assertTrue(strategy.calculateTotal(request) >= 0.0,
                    strategy.name() + " returned a negative price for " + request);
        }
    }

    @ParameterizedTest(name = "never costs more than twice the full price")
    @MethodSource("allStrategies")
    void price_isNeverAboveDoubleFullPrice(PricingStrategy strategy) {
        for (TicketRequest request : sampleRequests()) {
            double full = request.basePrice() * request.quantity();
            assertTrue(strategy.calculateTotal(request) <= full * 2.0 + 1e-9);
        }
    }

    @ParameterizedTest
    @MethodSource("allStrategies")
    void name_isNotBlank(PricingStrategy strategy) {

        assertFalse(strategy.name().isBlank());
    }

    @Test
    void allStrategies_haveDifferentNames() {
        long distinct = allStrategies().map(PricingStrategy::name).distinct().count();
        org.junit.jupiter.api.Assertions.assertEquals(4, distinct);
    }
}
