package road;

import people.Human;
import vehicles.Vehicle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Ділянка дороги з транспортними засобами. */
public class Road {

    public List<Vehicle<? extends Human>> carsInRoad = new ArrayList<>();

    /** Кількість людей, які перевозяться всіма засобами на дорозі. */
    public int getCountOfHumans() {
        int count = 0;
        for (Vehicle<? extends Human> car : carsInRoad) {
            count += car.getOccupiedSeats();
        }
        return count;
    }

    /** Додає транспортний засіб з будь-яким типом пасажирів. */
    public void addCarToRoad(Vehicle<? extends Human> car) {
        if (car == null) {
            throw new IllegalArgumentException("Транспортний засіб не може бути null");
        }
        if (carsInRoad.contains(car)) {
            throw new IllegalArgumentException("Цей транспортний засіб уже на дорозі: " + car);
        }
        carsInRoad.add(car);
    }

    /** Додає кілька засобів одразу; підходить, наприклад, List<Taxi<Human>> або List<FireTruck>. */
    public void addCarsToRoad(Collection<? extends Vehicle<? extends Human>> cars) {
        for (Vehicle<? extends Human> car : cars) {
            addCarToRoad(car);
        }
    }

    /** Кількість пасажирів заданого виду (Firefighter.class, Policeman.class, Human.class...). */
    public int countPassengersOfType(Class<? extends Human> type) {
        int count = 0;
        for (Vehicle<? extends Human> car : carsInRoad) {
            for (Human human : car.getPassengers()) {
                if (type.isInstance(human)) {
                    count++;
                }
            }
        }
        return count;
    }
}
