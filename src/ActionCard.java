public class ActionCard extends Card {


    public ActionCard(ActionType type, Colors color) {
        super(color, type, null, null);
    }

    public String toString() {
        return getColor() + " " + getActionType();
    }
}
