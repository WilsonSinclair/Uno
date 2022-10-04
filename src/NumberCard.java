public class NumberCard extends Card {

    public NumberCard(Colors color, int number) {
        super(color, null, null, number);
    }

    public String toString() {
        return getColor() + " " + getNumber();
    }
}
