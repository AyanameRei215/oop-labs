package road;

import org.junit.jupiter.api.Test;
import people.Firefighter;
import people.Human;
import people.Passenger;
import people.Policeman;
import vehicles.Bus;
import vehicles.FireTruck;
import vehicles.PoliceCar;
import vehicles.Taxi;
import vehicles.VehicleException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoadTest {

    @Test
    void emptyRoadHasNoHumans() {
        Road road = new Road();

        assertEquals(0, road.getCountOfHumans());
    }

    @Test
    void countsHumansInAllVehiclesOnTheRoad() throws VehicleException {
        Bus<Human> bus = new Bus<>(10);
        bus.board(new Passenger("Іван"));
        bus.board(new Firefighter("Олег"));
        bus.board(new Policeman("Марія"));

        Taxi<Human> taxi = new Taxi<>();
        taxi.board(new Passenger("Петро"));
        taxi.board(new Passenger("Анна"));

        FireTruck fireTruck = new FireTruck();
        fireTruck.board(new Firefighter("Богдан"));
        fireTruck.board(new Firefighter("Сергій"));

        PoliceCar policeCar = new PoliceCar();
        policeCar.board(new Policeman("Оксана"));

        Road road = new Road();
        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);
        road.addCarToRoad(fireTruck);
        road.addCarToRoad(policeCar);

        assertEquals(4, road.carsInRoad.size());
        assertEquals(8, road.getCountOfHumans());
    }

    @Test
    void countChangesWhenPassengerLeavesVehicle() throws VehicleException {
        Bus<Human> bus = new Bus<>(5);
        Human ivan = new Passenger("Іван");
        bus.board(ivan);
        bus.board(new Passenger("Петро"));

        Road road = new Road();
        road.addCarToRoad(bus);
        assertEquals(2, road.getCountOfHumans());

        bus.disembark(ivan);
        assertEquals(1, road.getCountOfHumans());
    }

    @Test
    void addingNullToRoadThrowsException() {
        Road road = new Road();

        assertThrows(IllegalArgumentException.class, () -> road.addCarToRoad(null));
    }

    @Test
    void sameVehicleCannotBeAddedTwice() {
        Road road = new Road();
        Taxi<Human> taxi = new Taxi<>();
        road.addCarToRoad(taxi);

        assertThrows(IllegalArgumentException.class, () -> road.addCarToRoad(taxi));
        assertEquals(1, road.carsInRoad.size());
    }

    @Test
    void addCarsToRoadAcceptsListOfSpecificVehicleType() throws VehicleException {
        Taxi<Human> first = new Taxi<>();
        first.board(new Passenger("Іван"));
        Taxi<Human> second = new Taxi<>();
        second.board(new Policeman("Марія"));
        List<Taxi<Human>> taxis = new ArrayList<>();
        taxis.add(first);
        taxis.add(second);

        Road road = new Road();
        road.addCarsToRoad(taxis);

        assertEquals(2, road.carsInRoad.size());
        assertEquals(2, road.getCountOfHumans());
    }

    @Test
    void countsPassengersOfGivenType() throws VehicleException {
        Bus<Human> bus = new Bus<>(5);
        bus.board(new Passenger("Іван"));
        bus.board(new Firefighter("Олег"));

        FireTruck fireTruck = new FireTruck();
        fireTruck.board(new Firefighter("Богдан"));
        fireTruck.board(new Firefighter("Сергій"));

        Road road = new Road();
        road.addCarToRoad(bus);
        road.addCarToRoad(fireTruck);

        assertEquals(3, road.countPassengersOfType(Firefighter.class));
        assertEquals(1, road.countPassengersOfType(Passenger.class));
        assertEquals(0, road.countPassengersOfType(Policeman.class));
        assertEquals(4, road.countPassengersOfType(Human.class));
    }
}
