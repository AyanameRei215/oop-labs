package vehicles;

/** Усі місця в транспортному засобі зайняті. */
public class NoFreeSeatsException extends VehicleException {

    public NoFreeSeatsException(String message) {
        super(message);
    }
}
