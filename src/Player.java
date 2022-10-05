import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class Player {

    private ArrayList<Card> hand;

    public Player(Game game) {
        hand = new ArrayList<>();
        this.game = game;
    }

    public Game game;

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
        if (card.getWildCardType() == WildCardType.Wild || card.getWildCardType() == WildCardType.DrawFour) {
            playedPile.push(card);
            System.out.println("New color? [Red, Yellow, Blue, Green]: ");
            String color;

            do {
                color = new Scanner(System.in).nextLine().toUpperCase();
                switch (color) {
                    case "RED" -> Game.setCurrentColor(Colors.RED);
                    case "YELLOW" -> Game.setCurrentColor(Colors.YELLOW);
                    case "BLUE" -> Game.setCurrentColor(Colors.BLUE);
                    case "GREEN" -> Game.setCurrentColor(Colors.GREEN);
                    default -> System.out.println("Invalid color! Choose again!");
                }
            } while(!isValidColor(color));
            if (card.getWildCardType() == WildCardType.DrawFour) {
                System.out.println("The next player draws 4 cards...");
                Game.nextPlayer.drawFromMainPile(game.mainPile, 4);
            }
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
        Colors color = game.getCurrentColor();
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

    public void drawFromMainPile(Stack<Card> mainPile, int numberToDraw) {
        while (numberToDraw != 0) {
            hand.add(mainPile.pop());
            numberToDraw--;
        }
    }

    public String toString() {
        return hand.toString();
    }
}
