package vehicles;

import people.Firefighter;

/** Пожежна машина: перевозить тільки пожежників, 6 місць. */
public class FireTruck extends Car<Firefighter> {

    public static final int SEATS = 6;

    public FireTruck() {
        super(SEATS);
    }
}
