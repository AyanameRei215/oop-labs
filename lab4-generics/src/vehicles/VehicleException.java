package vehicles;

/** Базовий клас виключних ситуацій при роботі з транспортним засобом. */
public class VehicleException extends Exception {

    public VehicleException(String message) {
        super(message);
    }
}
