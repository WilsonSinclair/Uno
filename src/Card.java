public class Card {
    //The color of the card
    private final Colors COLOR;

    //The Action Card type, if applicable
    private final ActionType ACTION_TYPE;

    //The Wild Card type, if applicable
    private final WildCardType WILD_CARD_TYPE;

    //The number on the card, if applicable
    private final Integer number;

    public Card(Colors color, ActionType type, WildCardType wildCardType, Integer number) {
        COLOR = color;
        ACTION_TYPE = type;
        WILD_CARD_TYPE = wildCardType;
        this.number = number;
    }

    //Gets the color of the Card
    public Colors getColor() {
        return COLOR;
    }

    //Get the ActionType of the Card
    public ActionType getActionType() {
        return ACTION_TYPE;
    }

    //Get the WildCardType of the Card
    public WildCardType getWildCardType() {
        return WILD_CARD_TYPE;
    }

    //Get the number of the Card
    public Integer getNumber() {
        return number;
    }
}
