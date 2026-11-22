package people;

/** Людина — базовий клас для всіх, хто може їхати в транспортному засобі. */
public abstract class Human {

    private final String name;

    public Human(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + name;
    }
}
