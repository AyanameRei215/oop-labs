package vehicles;

/** Указаний пасажир не сидить у транспортному засобі. */
public class PassengerNotFoundException extends VehicleException {

    public PassengerNotFoundException(String message) {
        super(message);
    }
}
