package vehicles;

import people.Human;

/** Автомобіль — проміжний клас ієрархії між Vehicle і конкретними машинами. */
public abstract class Car<T extends Human> extends Vehicle<T> {

    protected Car(int maxSeats) {
        super(maxSeats);
    }
}
