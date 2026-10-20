package concert.seating;

import concert.model.Seat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TogetherAllocation implements SeatAllocationStrategy {

    @Override
    public List<Seat> allocate(List<Seat> available, int quantity) {
        if (quantity <= 0) {
            return List.of();
        }
        Map<Integer, List<Seat>> rows = available.stream()
                .collect(Collectors.groupingBy(Seat::row, TreeMap::new, Collectors.toList()));

        List<Seat> best = List.of();
        double bestCost = Double.MAX_VALUE;
        for (List<Seat> row : rows.values()) {
            List<Seat> sorted = new ArrayList<>(row);
            sorted.sort(Comparator.comparingInt(Seat::number));
            for (int start = 0; start + quantity <= sorted.size(); start++) {
                List<Seat> window = sorted.subList(start, start + quantity);
                double cost = totalPrice(window);
                if (isNeighbours(window) && cost < bestCost) {
                    best = new ArrayList<>(window);
                    bestCost = cost;
                }
            }
        }
        return best;
    }

    private boolean isNeighbours(List<Seat> window) {
        for (int i = 1; i < window.size(); i++) {
            if (window.get(i).number() != window.get(i - 1).number() + 1) {
                return false;
            }
        }
        return true;
    }

    private double totalPrice(List<Seat> seats) {

        return seats.stream().mapToDouble(Seat::price).sum();
    }

    @Override
    public String name() {
        return "Seats together";
    }
}
