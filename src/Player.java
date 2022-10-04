import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class Player {

    private ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void populateHand(Stack<Card> pile) {
        for (int i = 0; i < 7; i++) {
            hand.add(pile.pop());
        }
    }

    public void playCard(Card card, Stack<Card> playedPile) {
        if (playedPile.isEmpty()) return;

        if (card.getColor() == Colors.COLORLESS) {
            playedPile.push(card);
            hand.remove(card);
        }
        else if (card.getColor() == playedPile.peek().getColor()) {
            playedPile.push(card);
            hand.remove(card);
        }
    }

    public boolean hasPlayableCard(Card card, Game game) {
        //If the first card in the game is a wild card or draw 4.
        if (game.getCurrentColor() == Colors.COLORLESS) { return true; }
        Colors color = card.getColor();
        ActionType type = card.getActionType();
        Integer number = card.getNumber();
        for (Card c : hand) {
            if (c.getClass() != ActionCard.class && c.getClass() != WildCard.class) {
                if (c.getColor() == color || Objects.equals(c.getNumber(), number)) return true;
            }
            if (c.getClass() == ActionCard.class) {
                if (c.getColor() == color || c.getActionType() == type) return true;
            }
            if (c.getClass() == WildCard.class) {
               if (c.getColor() == game.getCurrentColor() || c.getWildCardType() != null) return true;
            }
        }
        return false;
    }

    public void drawFromMainPile(Stack<Card> mainPile) {
        hand.add(mainPile.pop());
    }

    public String toString() {
        return hand.toString();
    }
}
