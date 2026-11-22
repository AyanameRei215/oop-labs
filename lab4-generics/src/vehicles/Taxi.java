package vehicles;

import people.Human;

/** Таксі: може перевозити будь-яких пасажирів, 4 місця. */
public class Taxi<T extends Human> extends Car<T> {

    public static final int SEATS = 4;

    public Taxi() {
        super(SEATS);
    }
}
