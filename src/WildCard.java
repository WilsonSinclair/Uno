public class WildCard extends Card {

    public WildCard(WildCardType type) {
        super(Colors.COLORLESS, null, type, null);
    }

    public String toString() {
        return getWildCardType().toString();
    }
}
