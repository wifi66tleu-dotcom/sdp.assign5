package concert;
import concert.data.SalesDataSource;
import concert.data.SimulatedSalesDataSource;
import concert.model.CustomerType;
import concert.model.Seat;
import concert.model.TicketRequest;
import concert.pricing.DemandBasedPricing;
import concert.pricing.EarlyBirdPricing;
import concert.pricing.GroupBundlePricing;
import concert.pricing.PricingContext;
import concert.pricing.PricingSelector;
import concert.pricing.PricingStrategy;
import concert.pricing.StandardPricing;
import concert.seating.BestViewAllocation;
import concert.seating.CheapestAllocation;
import concert.seating.SeatAllocationContext;
import concert.seating.SeatAllocationSelector;
import concert.seating.SeatAllocationStrategy;
import concert.seating.TogetherAllocation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConcertTicketingDemo {

    private static final LocalDate TODAY = LocalDate.of(2026, 10, 19);

    public static void main(String[] args) {
        SalesDataSource sales = new SimulatedSalesDataSource(0.30)
                .withEvent("sold-out-gala", 0.95)
                .withEvent("half-full-jazz", 0.55);

        pricingDemo(sales);
        seatingDemo();
    }

    private static void pricingDemo(SalesDataSource sales) {
        TicketRequest request = request("half-full-jazz", 2, 15000, 45, CustomerType.REGULAR);

        title("1. PRICING: same input, all strategies");
        System.out.println("Input: " + describe(request));
        List<PricingStrategy> all = List.of(
                new StandardPricing(), new EarlyBirdPricing(),
                new DemandBasedPricing(sales), new GroupBundlePricing());
        for (PricingStrategy strategy : all) {
            System.out.printf("  %-14s -> %10.2f KZT%n", strategy.name(), strategy.calculateTotal(request));
        }

        title("2. PRICING: manual choice vs. automatic choice by the Selector");
        PricingSelector selector = new PricingSelector(sales);
        PricingContext context = new PricingContext(new StandardPricing());
        List<TicketRequest> scenarios = List.of(
                request("half-full-jazz", 2, 15000, 45, CustomerType.REGULAR),
                request("sold-out-gala", 2, 15000, 45, CustomerType.REGULAR),
                request("half-full-jazz", 6, 15000, 5, CustomerType.REGULAR),
                request("rock-fest", 1, 15000, 3, CustomerType.REGULAR));
        for (TicketRequest scenario : scenarios) {
            context.setStrategy(new StandardPricing());
            String manualName = context.strategyName();
            double manual = context.calculatePrice(scenario);
            context.setStrategy(selector.select(scenario));
            double automatic = context.calculatePrice(scenario);
            System.out.printf("  %s%n    manual  : %-13s -> %10.2f%n    selector: %-13s -> %10.2f%n",
                    describe(scenario), manualName, manual, context.strategyName(), automatic);
        }

        title("3. PRICING: switching the strategy at runtime on ONE context");
        PricingContext runtime = new PricingContext(new StandardPricing());
        TicketRequest order = request("sold-out-gala", 4, 20000, 70, CustomerType.REGULAR);
        System.out.println("Input: " + describe(order));
        for (PricingStrategy next : all) {
            runtime.setStrategy(next);
            System.out.printf("  setStrategy(%-12s) -> %10.2f KZT%n", runtime.strategyName(), runtime.calculatePrice(order));
        }
    }

    private static void seatingDemo() {
        List<Seat> hall = hall(4, 10);

        title("4. SEATING: same input, all strategies (4 tickets, hall 4 rows x 10 seats)");
        List<SeatAllocationStrategy> all = List.of(
                new BestViewAllocation(), new CheapestAllocation(), new TogetherAllocation());
        for (SeatAllocationStrategy strategy : all) {
            System.out.printf("  %-14s -> %s%n", strategy.name(), strategy.allocate(hall, 4));
        }

        title("5. SEATING: automatic choice by the Selector");
        SeatAllocationSelector selector = new SeatAllocationSelector();
        SeatAllocationContext context = new SeatAllocationContext(new CheapestAllocation());
        record Wish(int quantity, CustomerType type) { }
        for (Wish wish : List.of(new Wish(1, CustomerType.REGULAR), new Wish(3, CustomerType.REGULAR),
                new Wish(3, CustomerType.VIP), new Wish(8, CustomerType.REGULAR))) {
            context.setStrategy(selector.select(wish.quantity(), wish.type()));
            System.out.printf("  %d x %-7s -> %-14s %s%n", wish.quantity(), wish.type(),
                    context.strategyName(), context.reserve(hall, wish.quantity()));
        }

        title("6. SEATING: switching the strategy at runtime");
        context.setStrategy(new BestViewAllocation());
        System.out.println("  Best view : " + context.reserve(hall, 2));
        context.setStrategy(new CheapestAllocation());
        System.out.println("  Cheapest  : " + context.reserve(hall, 2));
    }

    private static TicketRequest request(String eventId, int quantity, double price, int daysLeft, CustomerType type) {
        return new TicketRequest(eventId, quantity, price, TODAY, TODAY.plusDays(daysLeft), type);
    }

    private static String describe(TicketRequest r) {
        return String.format("%s, %d tickets x %.0f KZT, %d days left, %s",
                r.eventId(), r.quantity(), r.basePrice(),
                java.time.temporal.ChronoUnit.DAYS.between(r.purchaseDate(), r.eventDate()), r.customerType());
    }

    private static List<Seat> hall(int rows, int perRow) {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= rows; row++) {
            for (int number = 1; number <= perRow; number++) {
                seats.add(new Seat(row, number, 25000 - 5000 * (row - 1)));
            }
        }
        return seats;
    }

    private static void title(String text) {
        System.out.println();
        System.out.println("=== " + text + " ===");
    }
}
