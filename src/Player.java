import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
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
        if (card.getWildCardType() == WildCardType.Wild) {
            playedPile.push(card);
            System.out.println("New color? [Red, Yellow, Blue, Green]: ");
            String color = new Scanner(System.in).nextLine().toUpperCase();

            do {
                switch (color) {
                    case "RED" -> Game.setCurrentColor(Colors.RED);
                    case "YELLOW" -> Game.setCurrentColor(Colors.YELLOW);
                    case "BLUE" -> Game.setCurrentColor(Colors.BLUE);
                    case "GREEN" -> Game.setCurrentColor(Colors.GREEN);
                    default -> System.out.println("Invalid color!");
                }
            } while(!isValidColor(color));
        }
        else if (card.getColor() == playedPile.peek().getColor()) {
            playedPile.push(card);
            Game.setCurrentColor(card.getColor());
        }
        else if (card.getActionType() == playedPile.peek().getActionType()) {
            playedPile.push(card);
            Game.setCurrentColor(card.getColor());
        }
        hand.remove(card);
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

    public boolean isValidColor(String color) {
       return color.equalsIgnoreCase("RED")
               || color.equalsIgnoreCase("GREEN")
               || color.equalsIgnoreCase("BLUE")
               || color.equalsIgnoreCase("YELLOW");
    }

    public void drawFromMainPile(Stack<Card> mainPile) {
        hand.add(mainPile.pop());
    }

    public String toString() {
        return hand.toString();
    }
}
