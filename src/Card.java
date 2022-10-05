public class Card {
    private final Colors COLOR;
    private final ActionType TYPE;
    private final WildCardType WILD_CARD_TYPE;
    private final Integer number;

    public Card(Colors color, ActionType type, WildCardType wildCardType, Integer number) {
        COLOR = color;
        TYPE = type;
        WILD_CARD_TYPE = wildCardType;
        this.number = number;
    }

    //Get the color of the Card
    public Colors getColor() {
        return COLOR;
    }

    public ActionType getActionType() {
        return TYPE;
    }

    public WildCardType getWildCardType() {
        return WILD_CARD_TYPE;
    }

    public Integer getNumber() {
        return number;
    }
}
