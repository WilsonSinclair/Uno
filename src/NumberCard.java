public class NumberCard extends Card {
    private final int NUMBER;

    public NumberCard(Colors color, int number) {
        super(color);
        NUMBER = number;
    }

    private int getNumber() {
        return NUMBER;
    }

    public String toString() {
        return getColor() + " " + getNumber();
    }
}
