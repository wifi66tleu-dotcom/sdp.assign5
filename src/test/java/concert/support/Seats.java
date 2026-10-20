package concert.support;

import concert.model.Seat;
import java.util.ArrayList;
import java.util.List;

public final class Seats {

    private Seats() {
    }

    public static List<Seat> grid(int rows, int seatsPerRow) {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= rows; row++) {
            for (int number = 1; number <= seatsPerRow; number++) {
                seats.add(new Seat(row, number, 250.0 - 50.0 * row));
            }
        }
        return seats;
    }
}
