import people.Firefighter;
import people.Human;
import people.Passenger;
import people.Policeman;
import road.Road;
import vehicles.Bus;
import vehicles.FireTruck;
import vehicles.NoFreeSeatsException;
import vehicles.PassengerNotFoundException;
import vehicles.PoliceCar;
import vehicles.Taxi;
import vehicles.VehicleException;

public class Main {

    public static void main(String[] args) throws VehicleException {
        Bus<Human> bus = new Bus<>(5);
        bus.board(new Passenger("Іван"));
        bus.board(new Firefighter("Олег"));
        bus.board(new Policeman("Марія"));

        Taxi<Human> taxi = new Taxi<>();
        taxi.board(new Passenger("Петро"));
        taxi.board(new Policeman("Андрій"));

        FireTruck fireTruck = new FireTruck();
        fireTruck.board(new Firefighter("Богдан"));
        fireTruck.board(new Firefighter("Сергій"));

        PoliceCar policeCar = new PoliceCar();
        policeCar.board(new Policeman("Оксана"));
        // fireTruck.board(new Passenger("Іван"));  // помилка компіляції: пожежна машина лише для пожежників

        Road road = new Road();
        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);
        road.addCarToRoad(fireTruck);
        road.addCarToRoad(policeCar);

        for (var car : road.carsInRoad) {
            System.out.println(car + ": " + car.getPassengers());
        }
        System.out.println("Людей на дорозі: " + road.getCountOfHumans());
        System.out.println("Пожежників на дорозі: " + road.countPassengersOfType(Firefighter.class));
        System.out.println("Поліцейських на дорозі: " + road.countPassengersOfType(Policeman.class));

        // Виключні ситуації
        try {
            for (int i = 0; i < FireTruck.SEATS; i++) {
                fireTruck.board(new Firefighter("Пожежник " + i));
            }
        } catch (NoFreeSeatsException e) {
            System.out.println("Посадка неможлива: " + e.getMessage());
        }
        try {
            policeCar.disembark(new Policeman("Невідомий"));
        } catch (PassengerNotFoundException e) {
            System.out.println("Висадка неможлива: " + e.getMessage());
        }
    }
}
