package vehicles;

import org.junit.jupiter.api.Test;
import people.Firefighter;
import people.Human;
import people.Passenger;
import people.Policeman;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VehicleTest {

    @Test
    void busAcceptsAllKindsOfPassengers() throws VehicleException {
        Bus<Human> bus = new Bus<>(10);
        bus.board(new Passenger("Іван"));
        bus.board(new Firefighter("Олег"));
        bus.board(new Policeman("Марія"));

        assertEquals(10, bus.getMaxSeats());
        assertEquals(3, bus.getOccupiedSeats());
    }

    @Test
    void taxiAcceptsAllKindsOfPassengers() throws VehicleException {
        Taxi<Human> taxi = new Taxi<>();
        taxi.board(new Passenger("Іван"));
        taxi.board(new Firefighter("Олег"));
        taxi.board(new Policeman("Марія"));

        assertEquals(Taxi.SEATS, taxi.getMaxSeats());
        assertEquals(3, taxi.getOccupiedSeats());
    }

    @Test
    void fireTruckCarriesFirefighters() throws VehicleException {
        FireTruck truck = new FireTruck();
        truck.board(new Firefighter("Богдан"));
        truck.board(new Firefighter("Сергій"));

        assertEquals(FireTruck.SEATS, truck.getMaxSeats());
        assertEquals(2, truck.getOccupiedSeats());
        // truck.board(new Passenger("Іван"));  — не компілюється: машина лише для пожежників
    }

    @Test
    void policeCarCarriesPolicemen() throws VehicleException {
        PoliceCar car = new PoliceCar();
        car.board(new Policeman("Оксана"));

        assertEquals(PoliceCar.SEATS, car.getMaxSeats());
        assertEquals(1, car.getOccupiedSeats());
        // car.board(new Firefighter("Олег"));  — не компілюється: машина лише для поліцейських
    }

    @Test
    void busCanBeRestrictedToOneKindOfPassengers() throws VehicleException {
        Bus<Firefighter> firefighterBus = new Bus<>(3);
        firefighterBus.board(new Firefighter("Олег"));

        assertEquals(1, firefighterBus.getOccupiedSeats());
    }

    @Test
    void boardingFullVehicleThrowsException() throws VehicleException {
        Bus<Passenger> bus = new Bus<>(2);
        bus.board(new Passenger("Іван"));
        bus.board(new Passenger("Петро"));

        assertThrows(NoFreeSeatsException.class, () -> bus.board(new Passenger("Марія")));
        assertEquals(2, bus.getOccupiedSeats());
    }

    @Test
    void fullFireTruckRejectsSeventhFirefighter() throws VehicleException {
        FireTruck truck = new FireTruck();
        for (int i = 0; i < FireTruck.SEATS; i++) {
            truck.board(new Firefighter("Пожежник " + i));
        }

        assertEquals(truck.getMaxSeats(), truck.getOccupiedSeats());
        assertThrows(NoFreeSeatsException.class, () -> truck.board(new Firefighter("Зайвий")));
    }

    @Test
    void disembarkFreesSeat() throws VehicleException {
        Bus<Passenger> bus = new Bus<>(1);
        Passenger ivan = new Passenger("Іван");
        bus.board(ivan);

        bus.disembark(ivan);
        assertEquals(0, bus.getOccupiedSeats());

        bus.board(new Passenger("Петро"));
        assertEquals(1, bus.getOccupiedSeats());
    }

    @Test
    void disembarkingPassengerWhoIsNotInVehicleThrowsException() throws VehicleException {
        Bus<Human> bus = new Bus<>(5);
        bus.board(new Passenger("Іван"));
        Human stranger = new Passenger("Стороння людина");

        assertThrows(PassengerNotFoundException.class, () -> bus.disembark(stranger));
        assertEquals(1, bus.getOccupiedSeats());
    }

    @Test
    void disembarkingTheSamePassengerTwiceThrowsException() throws VehicleException {
        Taxi<Human> taxi = new Taxi<>();
        Human ivan = new Passenger("Іван");
        taxi.board(ivan);
        taxi.disembark(ivan);

        assertThrows(PassengerNotFoundException.class, () -> taxi.disembark(ivan));
    }

    @Test
    void boardingTheSamePassengerTwiceThrowsException() throws VehicleException {
        Bus<Human> bus = new Bus<>(5);
        Human ivan = new Passenger("Іван");
        bus.board(ivan);

        assertThrows(IllegalArgumentException.class, () -> bus.board(ivan));
        assertEquals(1, bus.getOccupiedSeats());
    }

    @Test
    void boardingNullThrowsException() {
        Bus<Human> bus = new Bus<>(5);

        assertThrows(IllegalArgumentException.class, () -> bus.board(null));
    }

    @Test
    void vehicleWithNonPositiveSeatsCannotBeCreated() {
        assertThrows(IllegalArgumentException.class, () -> new Bus<Human>(0));
        assertThrows(IllegalArgumentException.class, () -> new Bus<Human>(-3));
    }

    @Test
    void passengerListCannotBeModifiedFromOutside() throws VehicleException {
        Bus<Human> bus = new Bus<>(5);
        bus.board(new Passenger("Іван"));

        assertThrows(UnsupportedOperationException.class,
                () -> bus.getPassengers().add(new Passenger("Петро")));
        assertEquals(1, bus.getOccupiedSeats());
    }
}
