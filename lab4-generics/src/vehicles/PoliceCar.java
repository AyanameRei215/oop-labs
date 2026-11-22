package vehicles;

import people.Policeman;

/** Поліцейська машина: перевозить тільки поліцейських, 4 місця. */
public class PoliceCar extends Car<Policeman> {

    public static final int SEATS = 4;

    public PoliceCar() {
        super(SEATS);
    }
}
