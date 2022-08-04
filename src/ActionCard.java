public class ActionCard extends Card {

    private final ActionType TYPE;

    public ActionCard(ActionType type, Colors color) {
        super(color);
        TYPE = type;
    }

    public ActionType getActionType() {
        return TYPE;
    }

    public String toString() {
        return getColor() + " " + getActionType();
    }
}
