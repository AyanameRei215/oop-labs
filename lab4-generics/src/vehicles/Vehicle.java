package vehicles;

import people.Human;

import java.util.ArrayList;
import java.util.List;

/**
 * Транспортний засіб. Параметр T обмежує, яких пасажирів можна перевозити:
 * Vehicle<Human> — будь-яких, Vehicle<Firefighter> — тільки пожежників.
 */
public abstract class Vehicle<T extends Human> {

    private final int maxSeats;
    private final List<T> passengers = new ArrayList<>();

    protected Vehicle(int maxSeats) {
        if (maxSeats <= 0) {
            throw new IllegalArgumentException("Кількість місць має бути додатною: " + maxSeats);
        }
        this.maxSeats = maxSeats;
    }

    /** Максимальна кількість місць. */
    public int getMaxSeats() {
        return maxSeats;
    }

    /** Кількість зайнятих місць. */
    public int getOccupiedSeats() {
        return passengers.size();
    }

    /** Список пасажирів (копія, змінити її не можна). */
    public List<T> getPassengers() {
        return List.copyOf(passengers);
    }

    /** Посадка пасажира. Якщо місць немає — NoFreeSeatsException. */
    public void board(T passenger) throws NoFreeSeatsException {
        if (passenger == null) {
            throw new IllegalArgumentException("Пасажир не може бути null");
        }
        if (passengers.contains(passenger)) {
            throw new IllegalArgumentException("Пасажир уже сидить у транспортному засобі: " + passenger);
        }
        if (passengers.size() >= maxSeats) {
            throw new NoFreeSeatsException("Усі " + maxSeats + " місць зайнято");
        }
        passengers.add(passenger);
    }

    /** Висадка пасажира. Якщо він не сидить у засобі — PassengerNotFoundException. */
    public void disembark(T passenger) throws PassengerNotFoundException {
        if (!passengers.remove(passenger)) {
            throw new PassengerNotFoundException("Пасажир не сидить у транспортному засобі: " + passenger);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + passengers.size() + "/" + maxSeats + "]";
    }
}
