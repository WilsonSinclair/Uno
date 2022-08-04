public class WildCard extends Card {

    private final WildCardType TYPE;

    public WildCard(WildCardType type) {
        super(Colors.COLORLESS);
        TYPE = type;
    }

    public WildCardType getTYPE() {
        return TYPE;
    }

    public String toString() {
        return getTYPE().toString();
    }
}
