package vehicles;

import people.Human;

/** Автобус: може перевозити будь-яких пасажирів (залежно від параметра T). */
public class Bus<T extends Human> extends Vehicle<T> {

    public Bus(int maxSeats) {
        super(maxSeats);
    }
}
